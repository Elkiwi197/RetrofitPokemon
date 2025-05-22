package com.example.retrofitpokemon.ui.pantallaListaPokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitpokemon.common.ConstantesErrores
import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.domain.usecases.GetPokemonsUseCase
import com.example.retrofitpokemon.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentListaPokemonViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(ListaPokemonContract.ListaPokemonState())
    val uiState: LiveData<ListaPokemonContract.ListaPokemonState> get() = _uiState

    fun handleEvent(event: ListaPokemonContract.ListaPokemonEvent) {
        when (event) {
            is ListaPokemonContract.ListaPokemonEvent.LoadPokemons -> getPokemons()
            is ListaPokemonContract.ListaPokemonEvent.UiEventDone -> clearUiEvents()
        }
    }

    private fun getPokemons() {
        _uiState.value = _uiState.value?.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = getPokemonsUseCase()) {
                is NetworkResult.Success -> _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    listaPokemons = result.data!!
                )

                is NetworkResult.Error -> _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    uiEvent = UiEvent.ShowSnackbar(message = result.message!!)
                )

                is NetworkResult.Loading -> _uiState.value = _uiState.value?.copy(isLoading = true)
            }
        }
    }

    private fun clearUiEvents() {
        _uiState.value = _uiState.value?.copy(uiEvent = null)
    }


}