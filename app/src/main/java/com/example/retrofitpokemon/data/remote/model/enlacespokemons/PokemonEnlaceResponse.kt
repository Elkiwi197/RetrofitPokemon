package com.example.retrofitpokemon.data.remote.model.enlacespokemons

data class PokemonEnlaceResponse (
    val count: Int,
    val next: String?,
    val previous: String?,
    val pokemonEnlaces: List<PokemonEnlace>
)