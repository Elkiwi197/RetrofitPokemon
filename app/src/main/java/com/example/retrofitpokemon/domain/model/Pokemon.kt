package com.example.retrofitpokemon.domain.model

data class Pokemon (
    val id: Int,
    val nombre: String,
    val tipos: List<String>,
    val habilidades: List<String>,
    val urlFoto: String
)