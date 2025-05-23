package com.example.retrofitpokemon.data.remote.model.enlacestipos

data class TiposResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<TipoResult>
)