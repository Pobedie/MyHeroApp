package com.example.myheroapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.SuperheroApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

private val TAG = "HomeScreenViewModel"

class HomeScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    public val uiState = _uiState.asStateFlow()
    var superheroApiState: SuperheroApiState by mutableStateOf(SuperheroApiState.Loading)
        private set

    init {
        getHeroesInfo()
    }

    fun getHeroesInfo(){
        viewModelScope.launch {
            try {
            val deferredResults = (1..2).map { id ->
                async {
                    SuperheroApi.retrofitService.getFullInfo(id)
                }
            }
            val listResults = deferredResults.awaitAll()
            val validResults = listResults.filter { it.response != "error" }

            superheroApiState = SuperheroApiState.Success(result = validResults)
            } catch (e: IOException) {
                superheroApiState = SuperheroApiState.Error
            }
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