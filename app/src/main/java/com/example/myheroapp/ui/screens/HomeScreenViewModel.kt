package com.example.myheroapp.ui.screens

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myheroapp.data.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import db.HeroEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val heroRepository: HeroRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    public val uiState = _uiState.asStateFlow()
    var superheroApiState: HeroRepository.SuperheroApiState by mutableStateOf(HeroRepository.SuperheroApiState.Loading)
        private set
    val allHeroes = flow<List<HeroEntity>> {
        while (true){
            val allHeroes = heroRepository.getAllHeroes(
                uiState.value.filterByPublisher,
                uiState.value.showOnlyFavorites
            )
            if (allHeroes.isEmpty()){
                fetchMoreHeroes()
            } else {
                emit(allHeroes)
                superheroApiState = HeroRepository.SuperheroApiState.Success
            }
            delay(1_000)
        }
    }

    init {
        selectPublishers("")
    }

    fun fetchMoreHeroes() {
        viewModelScope.launch {
            heroRepository.fetchHeroes()
        }
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
    val lazyListState: LazyListState = LazyListState(),
    val publishersList: List<String> = listOf(),
)
