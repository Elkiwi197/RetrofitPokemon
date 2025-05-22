package com.example.retrofitpokemon.ui.pantallaListaPokemon

import com.example.retrofitpokemon.domain.model.Pokemon
import com.example.retrofitpokemon.ui.common.UiEvent

interface ListaPokemonContract {
    sealed class ListaPokemonEvent {
        data object LoadPokemons : ListaPokemonEvent()
        data object UiEventDone : ListaPokemonEvent()
    }

    data class ListaPokemonState(
        val isLoading: Boolean = false,
        val listaPokemons: List<Pokemon> = emptyList(),
        val uiEvent: UiEvent? = null
    )
}