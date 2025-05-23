package com.example.retrofitpokemon.domain.usecases

import com.example.retrofitpokemon.data.repositories.TiposRepository
import javax.inject.Inject

class GetTiposUseCase @Inject constructor(
    private val tiposRepository: TiposRepository
) {
    suspend operator fun invoke() = tiposRepository.getAllTipos()
}