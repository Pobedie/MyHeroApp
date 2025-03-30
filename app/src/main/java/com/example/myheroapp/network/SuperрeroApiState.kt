package com.example.myheroapp.network


sealed interface SuperheroApiState {
    object Success : SuperheroApiState
    object Error : SuperheroApiState
    object Loading : SuperheroApiState
}
