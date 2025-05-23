package com.example.retrofitpokemon.data.remote.apiservices

import com.example.retrofitpokemon.data.remote.model.enlacestipos.TiposResponse
import com.example.retrofitpokemon.data.remote.model.tipodatabase.TipoDatabase
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TiposService {

    @GET("type")
    suspend fun getTipos() : Response<TiposResponse>

    @GET("type/{id}")
    suspend fun getTipoById(@Path("id") id: Int): Response<TipoDatabase>
}