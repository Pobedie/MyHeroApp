package com.example.myheroapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myheroapp.data.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import db.HeroEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"
private const val ELEMENTS_PER_PAGE = 20

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val heroRepository: HeroRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    public val uiState = _uiState.asStateFlow()
    var superheroApiState: SuperheroApiState by mutableStateOf(SuperheroApiState.Loading)
        private set

    init {
        getHeroesInfo()
        selectPublishers("")
    }

    fun getHeroesInfo() {
        if (uiState.value.showOnlyFavorites==true || uiState.value.filterByPublisher!=""){
            selectHeroByPublisherAndFavorite()
        }else{
            viewModelScope.launch {
                try {
                    val loadFromID = uiState.value.loadedFromID
                    val deferredResults = (1..loadFromID+ELEMENTS_PER_PAGE).map { id ->
                        async {
                            return@async heroRepository.selectHeroById(id.toString())
                        }
                    }

                    val listResults = deferredResults.awaitAll()
                    val validResults = listResults.filterNotNull()

                    if (validResults.isNotEmpty()) {
                        superheroApiState = SuperheroApiState.Success
                        updateHeroList(validResults)
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
    }

    fun loadMoreElements(){
        _uiState.update { state ->
            state.copy(loadedFromID = state.loadedFromID + ELEMENTS_PER_PAGE+1)
        }
        getHeroesInfo()
    }

    fun publisherImage(publisher: String): Int{
        return heroRepository.getPublisherImage(publisher)
    }

    fun changeShowOnlyFavorites(){
        val currentState = uiState.value.showOnlyFavorites
        _uiState.update { state ->
            state.copy(
                showOnlyFavorites = !currentState
            )
        }
        selectHeroByPublisherAndFavorite()
    }

    fun updateFilterByPublisher(publisher: String){
        _uiState.update { state ->
            state.copy(
                filterByPublisher = publisher
            )
        }
        selectHeroByPublisherAndFavorite()
    }

    fun changeSearchIsActive(){
        val currentState = uiState.value.searchIsActive
        _uiState.update { state ->
            state.copy(
                searchIsActive = !currentState
            )
        }
    }

    fun selectHeroByPublisherAndFavorite(){
        viewModelScope.launch {
            try {
                val heroes = heroRepository.selectHeroByPublisherAndFavorite(
                    uiState.value.filterByPublisher,
                    uiState.value.showOnlyFavorites)
                updateHeroList(heroes.first())
                superheroApiState = SuperheroApiState.Success
            } catch (e: Exception){
                superheroApiState = SuperheroApiState.Error
                Log.e(TAG, "Unexpected error: $e")
            }
        }
    }

    private fun updateHeroList(heroList: List<HeroEntity>){
        _uiState.update { state ->
            state.copy(
                heroList = heroList
            )
        }
    }

    private fun addToHeroList(heroList: List<HeroEntity>){
        _uiState.update { state ->
            state.copy(
                heroList = state.heroList + heroList
            )
        }
    }

    fun selectPublishers(searchPublishers: String){
        viewModelScope.launch() {
            val publishers = heroRepository.selectPublishers(searchPublishers)
            updatePublishersList(publishers.first())
        }
    }

    private fun updatePublishersList(publishersList: List<String>){
        _uiState.update { state ->
            state.copy(
                publishersList = publishersList
            )
        }
    }

}

data class HomeScreenUiState(
    val showOnlyFavorites: Boolean = false,
    val filterByPublisher: String = "",
    val searchIsActive: Boolean = false,
    val loadedFromID: Int = 1,
    val publishersList: List<String> = listOf(),
    val heroList: List<HeroEntity> = listOf()
)

sealed interface SuperheroApiState {
    object Success : SuperheroApiState
    object Error : SuperheroApiState
    object Loading : SuperheroApiState
}