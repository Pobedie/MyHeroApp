package com.example.myheroapp.data

import android.util.Log
import com.example.myheroapp.network.SuperheroApi
import com.example.myheroapp.utils.getPublisherImg
import com.example.myheroapp.utils.toHeroEntity
import db.HeroEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

private const val TAG = "HeroRepository"
private const val ELEMENTS_PER_PAGE = 20

class HeroRepository @Inject constructor(
    private val heroDataSource: HeroDataSource
) {
    suspend fun fetchHeroes(): SuperheroApiState{
        try {
            val loadFromID = getAllHeroes().size
            val deferredResults = (1..loadFromID+ ELEMENTS_PER_PAGE).map { id ->
                val heroFromDb = heroDataSource.selectHeroById(id.toString())
                if (heroFromDb != null){
                    Log.i(TAG, "Hero found in db, loading from there: $heroFromDb")
                    heroFromDb
                } else {
                    try {
                        val heroFromApi = SuperheroApi.retrofitService.getFullInfo(id.toInt())
                        if (heroFromApi.response != "error"){
                            insertHeroInDatabase(heroFromApi.toHeroEntity())
                            Log.i(TAG, "Hero not found in db, loading from site: $heroFromApi")
                            heroFromApi.toHeroEntity()
                        } else {
                            null
                        }
                    } catch (e: IOException) {
                        Log.e(TAG, "Failed to fetch hero from API: ${e.message}")
                        null
                    }
                }
            }

            val validResults = deferredResults.filterNotNull()

            if (validResults.isNotEmpty()) {
                return SuperheroApiState.Success
            } else {
                Log.e(TAG, "No valid heroes found in db or API")
                return SuperheroApiState.Error
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error: ${e.message}")
            return SuperheroApiState.Error
        }
    }

    suspend fun selectHeroById(id: Int): HeroEntity{
        return heroDataSource.selectHeroById(id.toString())!!
    }

    suspend fun getAllHeroes(
        filterByPublisher: String = "",
        filterByFavorites: Boolean = false
    ): List<HeroEntity>{
        return if (filterByPublisher=="" && !filterByFavorites){
            heroDataSource.selectAllHeroes().first()
        } else if (filterByFavorites) {
            heroDataSource.selectHeroesByPublisherAndFavorite(
                filterByPublisher,
                true
            ).first()
        } else {
            heroDataSource.selectHeroesByPublisher(filterByPublisher).first()
        }
    }

    private suspend fun insertHeroInDatabase(heroEntity: HeroEntity){
        heroDataSource.insertHero(heroEntity)
    }

    suspend fun updateHeroIsFavorite(heroEntity: HeroEntity, isFavorite: Boolean){
        heroDataSource.updateIsFavorite(heroEntity, isFavorite)
    }

    suspend fun selectPublishers(searchPublishers: String): Flow<List<String>>{
        return heroDataSource.selectPublishers(searchPublishers)
    }

    fun getPublisherImage(publisher: String): Int{
        return getPublisherImg(publisher)
    }

//    Maybe move this to its own file
    sealed interface SuperheroApiState {
        object Success : SuperheroApiState
        object Error : SuperheroApiState
        object Loading : SuperheroApiState
    }
}