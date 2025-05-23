package com.example.retrofitpokemon.ui.pantallaListaTipos

import com.example.retrofitpokemon.domain.model.Tipo
import com.example.retrofitpokemon.ui.common.UiEvent

interface ListaTiposContract {

    sealed class ListaTipoEvent {
        data object LoadTipos : ListaTipoEvent()
        data object UiEventDone : ListaTipoEvent()
    }

    data class ListaTiposState(
        val isLoading: Boolean = false,
        val listaTipos: List<Tipo> = emptyList(),
        val uiEvent: UiEvent? = null
    )
}
