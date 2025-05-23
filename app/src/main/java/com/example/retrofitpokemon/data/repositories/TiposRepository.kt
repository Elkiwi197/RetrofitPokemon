package com.example.retrofitpokemon.data.repositories

import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.data.remote.datasource.TipoDataSource
import com.example.retrofitpokemon.domain.model.Tipo
import javax.inject.Inject

class TiposRepository @Inject constructor(
    private val tipoDataSource: TipoDataSource
) {
    suspend fun getAllTipos(): NetworkResult<List<Tipo>> {
        return tipoDataSource.fetchTipos()
    }
}