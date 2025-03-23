package com.example.myheroapp.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myheroapp.data.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import db.HeroEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HeroPageScreenViewModel"

@HiltViewModel
class HeroPageScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val heroRepository: HeroRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HeroPageScreenUiState())
    public val uiState = _uiState.asStateFlow()
    private val heroId: String

    init {
        heroId = savedStateHandle.get<String>("heroId") ?: throw IllegalArgumentException("ID is required")
        viewModelScope.launch {
            getHeroInformation(heroId)
        }
    }

    private suspend fun getHeroInformation(id: String){
        val hero = heroRepository.selectHeroById(id)!!
        updateHeroEntry(heroEntity = hero)
    }

    fun updateIsFavorite(){
        val value = uiState.value.heroEntity.is_favorite != 1L
        viewModelScope.launch {
            heroRepository.updateHeroIsFavorite(uiState.value.heroEntity, value)
            getHeroInformation(heroId)
        }
    }

    fun publisherImage(): Int{
        return heroRepository.getPublisherImage(uiState.value.heroEntity.publisher)
    }

    private fun updateHeroEntry(heroEntity: HeroEntity){
        _uiState.update { state ->
            state.copy(
                heroEntity = heroEntity
            )
        }
    }

}

data class HeroPageScreenUiState(
    val heroEntity: HeroEntity = HeroEntity("","","","",
        "","","","","","","",
        "","","","","","",0)
)

