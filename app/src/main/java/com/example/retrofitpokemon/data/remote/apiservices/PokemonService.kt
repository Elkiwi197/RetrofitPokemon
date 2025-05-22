package com.example.retrofitpokemon.data.remote.apiservices

import com.example.retrofitpokemon.data.remote.model.enlacespokemons.PokemonEnlaceResponse
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.PokemonDatabase
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemons(@Query("url") url: String? = null): Response<PokemonEnlaceResponse>


    @GET("pokemon")
    suspend fun getEnlaces(): Response<List<PokemonEnlaceResponse>>

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0
    ): Response<PokemonEnlaceResponse>

    @GET
    suspend fun getPokemonByUrl(@Url url: String): Response<PokemonDatabase>

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): Response<PokemonDatabase>

    @DELETE("pokemon/{id}")
    suspend fun deletePokemon(@Path("id") id: Int): Response<PokemonDatabase>
}