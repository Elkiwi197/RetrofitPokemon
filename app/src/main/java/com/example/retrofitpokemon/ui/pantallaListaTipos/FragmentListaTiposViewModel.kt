package com.example.retrofitpokemon.ui.pantallaListaTipos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.domain.usecases.GetTiposUseCase
import com.example.retrofitpokemon.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentListaTiposViewModel @Inject constructor(
    private val getTiposUseCase: GetTiposUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(ListaTiposContract.ListaTiposState())
    val uiState: LiveData<ListaTiposContract.ListaTiposState> get() = _uiState

    fun handleEvent(event: ListaTiposContract.ListaTipoEvent) {
        when (event) {
            ListaTiposContract.ListaTipoEvent.LoadTipos -> getTipos()
            ListaTiposContract.ListaTipoEvent.UiEventDone -> clearUiEvents()
        }
    }

    private fun getTipos() {
        _uiState.value = _uiState.value?.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = getTiposUseCase()) {
                is NetworkResult.Success -> _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    listaTipos = result.data!!
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