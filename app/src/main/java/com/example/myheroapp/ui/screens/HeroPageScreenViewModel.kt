package com.example.myheroapp.ui.screens

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HeroPageScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HeroPageScreenUiState())
    public val uiState = _uiState.asStateFlow()

}

data class HeroPageScreenUiState(
    val isFavorite: Boolean = false
)