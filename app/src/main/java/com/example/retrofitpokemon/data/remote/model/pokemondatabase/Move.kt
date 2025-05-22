package com.example.retrofitpokemon.data.remote.model.pokemondatabase

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)