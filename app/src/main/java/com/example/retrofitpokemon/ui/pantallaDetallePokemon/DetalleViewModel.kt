package com.example.retrofitpokemon.ui.pantallaDetallePokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitpokemon.common.ConstantesErrores
import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.data.remote.mappers.PokemonMapper
import com.example.retrofitpokemon.domain.model.Pokemon
import com.example.retrofitpokemon.domain.usecases.GetPokemonByIdUseCase
import com.example.retrofitpokemon.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val getPokemonByIdUseCase: GetPokemonByIdUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(DetalleContract.PokemonState())
    val uiState: LiveData<DetalleContract.PokemonState> get() = _uiState

    fun handleEvent(event: DetalleContract.PokemonEvent) {
        when (event) {
            is DetalleContract.PokemonEvent.LoadPokemonById -> getPokemonById(event.id)
            is DetalleContract.PokemonEvent.UiEventDone -> clearUiEvents()
        }
    }

    private fun getPokemonById(id: Int) {
        _uiState.value = _uiState.value?.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = getPokemonByIdUseCase(id)) {
                is NetworkResult.Success -> _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    pokemon = result.data
                )

                is NetworkResult.Error -> _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    uiEvent = UiEvent.ShowSnackbar(
                        result.message ?: ConstantesErrores.ERROR_DESCONOCIDO
                    )
                )

                is NetworkResult.Loading -> _uiState.value =
                    _uiState.value?.copy(isLoading = true)
            }
        }
    }

    private fun clearUiEvents() {
        _uiState.value = _uiState.value?.copy(uiEvent = null)
    }


}
