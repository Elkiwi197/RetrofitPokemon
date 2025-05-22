package com.example.retrofitpokemon.ui.pantallaDetallePokemon

import com.example.retrofitpokemon.domain.model.Pokemon
import com.example.retrofitpokemon.ui.common.UiEvent

interface DetalleContract {
    sealed class PokemonEvent {
        data class LoadPokemonById(val id: Int) : PokemonEvent()
        data object UiEventDone : PokemonEvent()
    }

    data class PokemonState(
        val isLoading: Boolean = false,
        val pokemon: Pokemon? = null,
        val uiEvent: UiEvent? = null
    )
}