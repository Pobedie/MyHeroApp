package com.example.myheroapp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myheroapp.data.HeroDataSource
import com.example.myheroapp.data.HeroDataSourceImpl
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.SuperheroApi
import com.example.myheroapp.utils.heroEntityToHeroInfo
import com.example.sqldelight.db.HeroDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.xmlpull.v1.sax2.Driver
import java.io.IOException
import javax.inject.Inject

private val TAG = "HomeScreenViewModel"

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val heroDataSource: HeroDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    public val uiState = _uiState.asStateFlow()
    var superheroApiState: SuperheroApiState by mutableStateOf(SuperheroApiState.Loading)
        private set

    init {
        getHeroesInfo()
    }

    fun getHeroesInfo() {
        viewModelScope.launch {
            try {
                val deferredResults = (1..50).map { id ->
                    async {
                        val heroFromDb = heroDataSource.selectHeroById(id.toString())
                        if (heroFromDb != null) {
                            Log.i(TAG, "Hero found in db, loading from there: $heroFromDb")
                            return@async heroFromDb.heroEntityToHeroInfo()
                        } else {
                            try {
                                val heroFromApi = SuperheroApi.retrofitService.getFullInfo(id)
                                insertHeroInDatabase(heroFromApi)
                                Log.i(TAG, "Hero not found in db, loading from site: $heroFromApi")
                                return@async heroFromApi
                            } catch (e: IOException) {
                                Log.e(TAG, "Failed to fetch hero from API: ${e.message}")
                                return@async null
                            }
                        }
                    }
                }

                val listResults = deferredResults.awaitAll()
                val validResults = listResults.filterNotNull().filter { it.response != "error" }

                if (validResults.isNotEmpty()) {
                    superheroApiState = SuperheroApiState.Success(result = validResults)
                } else {
                    superheroApiState = SuperheroApiState.Error
                    Log.e(TAG, "No valid heroes found in db or API")
                }
            } catch (e: Exception) {
                superheroApiState = SuperheroApiState.Error
                Log.e(TAG, "Unexpected error: ${e.message}")
            }
        }
    }

    private fun insertHeroInDatabase(heroInfo: HeroInfo){
        viewModelScope.launch {
            heroDataSource.insertHero(heroInfo)
        }
    }

}

data class HomeScreenUiState(
    val searchQuery: String = "",
    val listResult: String = ""
)

sealed interface SuperheroApiState {
    data class Success(val result: List<HeroInfo>) : SuperheroApiState
    object Error : SuperheroApiState
    object Loading : SuperheroApiState
}