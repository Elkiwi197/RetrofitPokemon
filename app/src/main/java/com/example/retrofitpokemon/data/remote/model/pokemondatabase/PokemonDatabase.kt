package com.example.retrofitpokemon.data.remote.model.pokemondatabase

import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.domain.model.Pokemon

data class PokemonDatabase(
    val abilities: List<Ability>,
    val base_experience: Int,
    val cries: Cries,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<PastAbility>,
    val past_types: List<Any?>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)

fun PokemonDatabase.toPokemon() : NetworkResult<Pokemon> =
    NetworkResult.Success(Pokemon(
        id = id,
        nombre = name,
        tipos = types.map {
            it.type.name
        },
        habilidades = abilities.map {
            it.ability.name
        },
        urlFoto = sprites.front_default
    ))