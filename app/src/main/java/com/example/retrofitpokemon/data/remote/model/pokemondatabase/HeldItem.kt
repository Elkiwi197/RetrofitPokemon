package com.example.retrofitpokemon.data.remote.model.pokemondatabase

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)