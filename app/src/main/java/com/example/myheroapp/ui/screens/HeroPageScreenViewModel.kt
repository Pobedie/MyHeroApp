package com.example.myheroapp.ui.screens

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myheroapp.data.HeroDataSource
import com.example.myheroapp.network.Appearance
import com.example.myheroapp.network.Biography
import com.example.myheroapp.network.Connections
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.Image
import com.example.myheroapp.network.PowerStats
import com.example.myheroapp.network.Work
import com.example.myheroapp.utils.heroEntityToHeroInfo
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroPageScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val heroDataSource: HeroDataSource
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
        val hero = heroDataSource.selectHeroById(id)!!.heroEntityToHeroInfo()
        updateHeroInfo(heroInfo = hero)
    }

    fun updateIsFavorite(){
        val value = !uiState.value.heroInfo.isFavorite
        viewModelScope.launch {
            heroDataSource.updateIsFavorite(uiState.value.heroInfo, value)
            getHeroInformation(heroId)
        }
    }

    private fun updateHeroInfo(heroInfo: HeroInfo){
        _uiState.update { state ->
            state.copy(
                heroInfo = heroInfo,
            )
        }
    }

}

data class HeroPageScreenUiState(
    val heroInfo: HeroInfo = HeroInfo(
        response = "",
        id = "",
        name = "",
        powerStats = PowerStats("","","","","",""),
        appearance = Appearance("","", listOf("",""), listOf(),"",""),
        biography = Biography(fullName = "","",
            listOf(),"","", publisher = "",""),
        work = Work("",""),
        connections = Connections("",""),
        image = Image("")
    )
)

