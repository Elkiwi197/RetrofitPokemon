package com.example.retrofitpokemon.domain.usecases

import com.example.retrofitpokemon.data.repositories.PokemonRepository
import javax.inject.Inject

class GetPokemonByIdUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int) = pokemonRepository.getPokemonById(id)
}