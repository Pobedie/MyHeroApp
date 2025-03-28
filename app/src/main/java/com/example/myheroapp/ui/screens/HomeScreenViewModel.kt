package com.example.myheroapp.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
    val allHeroes = flow<List<HeroEntity>> {
        while (true){
            val allHeroes = heroRepository.getAllHeroes(
                uiState.value.filterByPublisher,
                uiState.value.showOnlyFavorites
            )
            emit(allHeroes)
            if (allHeroes.isEmpty()){
                getHeroesInfo()
            } else {
                superheroApiState = SuperheroApiState.Success
            }
            delay(1_000)
        }
    }

    init {
//        getHeroesInfo()
        selectPublishers("")
    }

    fun getHeroesInfo() {
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
    }

    fun updateFilterByPublisher(publisher: String){
        _uiState.update { state ->
            state.copy(
                filterByPublisher = publisher
            )
        }
    }

    fun changeSearchIsActive(){
        val currentState = uiState.value.searchIsActive
        _uiState.update { state ->
            state.copy(
                searchIsActive = !currentState
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

    fun updateLazyListState(listState: LazyListState){
        _uiState.update { state ->
            state.copy(
                lazyListState = listState
            )
        }
    }

}

data class HomeScreenUiState(
    val showOnlyFavorites: Boolean = false,
    val filterByPublisher: String = "",
    val searchIsActive: Boolean = false,
    val loadedFromID: Int = 1,
    val lazyListState: LazyListState = LazyListState(),
    val publishersList: List<String> = listOf(),
)

sealed interface SuperheroApiState {
    object Success : SuperheroApiState
    object Error : SuperheroApiState
    object Loading : SuperheroApiState
}