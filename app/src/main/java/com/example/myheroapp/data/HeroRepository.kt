package com.example.myheroapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.myheroapp.network.SuperheroApi
import com.example.myheroapp.utils.getPublisherImg
import com.example.myheroapp.utils.toHeroEntity
import db.HeroEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

private const val TAG = "HeroRepository"

class HeroRepository @Inject constructor(
    private val heroDataSource: HeroDataSource
) {
    suspend fun selectHeroById(id: String): HeroEntity?{
        val heroFromDb = heroDataSource.selectHeroById(id)
        if (heroFromDb != null){
            Log.i(TAG, "Hero found in db, loading from there: $heroFromDb")
            return heroFromDb
        } else {
            try {
                val heroFromApi = SuperheroApi.retrofitService.getFullInfo(id.toInt())
                if (heroFromApi.response != "error"){
                    insertHeroInDatabase(heroFromApi.toHeroEntity())
                    Log.i(TAG, "Hero not found in db, loading from site: $heroFromApi")
                    return heroFromApi.toHeroEntity()
                } else {
                    return null
                }
            } catch (e: IOException) {
                Log.e(TAG, "Failed to fetch hero from API: ${e.message}")
                return null
            }
        }
    }
    suspend fun getAllHeroes(
        filterByPublisher: String = "",
        filterByFavorites: Boolean = false
    ): List<HeroEntity>{
        return heroDataSource.selectHeroesByPublisherAndFavorite(
            filterByPublisher,
            filterByFavorites
        ).first()
    }
    private suspend fun insertHeroInDatabase(heroEntity: HeroEntity){
        heroDataSource.insertHero(heroEntity)
    }
    suspend fun selectHeroByPublisherAndFavorite(publisher: String, isFavorite: Boolean): Flow<List<HeroEntity>>{
        return heroDataSource.selectHeroesByPublisherAndFavorite(publisher,isFavorite)
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
}