package com.example.retrofitpokemon.data.remote.mappers

import com.example.retrofitpokemon.data.remote.model.enlacespokemons.PokemonEnlace
import com.example.retrofitpokemon.data.remote.model.enlacespokemons.PokemonEnlaceResponse

//todo igual no es necesario
object PokemonEnlaceMapper {
    fun responseToEnlaces(pokemonEnlaceResponse: PokemonEnlaceResponse): List<PokemonEnlace>{
        return pokemonEnlaceResponse.results
    }
}