package com.example.retrofitpokemon.data.remote.datasource

import com.example.retrofitpokemon.common.ConstantesErrores
import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.data.remote.apiservices.PokemonService
import com.example.retrofitpokemon.data.remote.mappers.PokemonMapper
import com.example.retrofitpokemon.di.IoDispatcher
import com.example.retrofitpokemon.domain.model.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PokemonDataSource @Inject constructor(
    private val pokemonService: PokemonService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun fetchPokemonById(id: Int): NetworkResult<Pokemon> {
        val llamada = safeApiCall {
            pokemonService.getPokemonById(id)
        }
        when (llamada) {
            is NetworkResult.Error -> return NetworkResult.Error(message = llamada.message)
            is NetworkResult.Loading -> return NetworkResult.Loading()
            is NetworkResult.Success -> {
                if (llamada.data != null) {
                    return NetworkResult.Success(data = PokemonMapper.pokemonDatabaseToPokemon(llamada.data!!))
                } else {
                    return NetworkResult.Error(message = ConstantesErrores.LLAMADA_VACIA)
                }
            }
        }
    }

    suspend fun fetchAllPokemons(): NetworkResult<List<Pokemon>> {
        val response = safeApiCall { pokemonService.getPokemons() }
        return when (response) {
            is NetworkResult.Success -> {
                val urls = response.data?.results?.map { it.url } ?: emptyList()
                val pokemons = urls.mapNotNull { url ->
                    val id = url.trimEnd('/').substringAfterLast('/').toIntOrNull()
                    if (id != null) {
                        val pokemonDatabase = safeApiCall { pokemonService.getPokemonById(id) }
                        when (pokemonDatabase) {
                            is NetworkResult.Success -> {
                                PokemonMapper.pokemonDatabaseToPokemon(pokemonDatabase.data!!)
                            }
                            else -> null
                        }
                    } else null
                }
                NetworkResult.Success(pokemons)
            }
            is NetworkResult.Error -> NetworkResult.Error(message = response.message)
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }


    suspend fun deletePokemon(id: Int): NetworkResult<Boolean> = withContext(dispatcher) {
        safeApiCallNoBody { pokemonService.deletePokemon(id) }
    }

}