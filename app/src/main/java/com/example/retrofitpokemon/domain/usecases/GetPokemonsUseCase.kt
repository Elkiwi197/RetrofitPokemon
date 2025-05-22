package com.example.retrofitpokemon.domain.usecases

import com.example.retrofitpokemon.data.repositories.PokemonRepository
import javax.inject.Inject


class GetPokemonsUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    // suspend operator fun invoke() = pokemonRepository.getAllPokemons()
}