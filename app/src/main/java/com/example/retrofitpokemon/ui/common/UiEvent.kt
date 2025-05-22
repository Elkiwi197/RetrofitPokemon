package com.example.retrofitpokemon.ui.common


sealed class UiEvent{

    data class Navigate(val route: String=""): UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()

}
