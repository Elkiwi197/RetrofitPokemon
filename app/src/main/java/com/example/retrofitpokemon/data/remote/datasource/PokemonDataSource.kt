package com.example.retrofitpokemon.data.remote.datasource

import com.example.retrofitpokemon.common.ConstantesErrores
import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.data.remote.apiservices.PokemonService
import com.example.retrofitpokemon.data.remote.flatMap
import com.example.retrofitpokemon.data.remote.mappers.PokemonMapper
import com.example.retrofitpokemon.data.remote.model.enlacespokemons.PokemonEnlaceResponse
import com.example.retrofitpokemon.di.IoDispatcher
import com.example.retrofitpokemon.domain.model.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PokemonDataSource @Inject constructor(
    private val pokemonService: PokemonService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun fetchPokemonById(id: Int): NetworkResult<Pokemon> {
        val call = safeApiCall {
            pokemonService.getPokemonById(id)
        }
        when (call) {
            is NetworkResult.Error -> return NetworkResult.Error(message = call.message)
            is NetworkResult.Loading -> return NetworkResult.Loading()
            is NetworkResult.Success -> {
                if (call.data != null) {
                    return NetworkResult.Success(data = PokemonMapper.pokemonDatabaseToPokemon(call.data!!))
                } else {
                    return NetworkResult.Error(message = ConstantesErrores.LLAMADA_VACIA)
                }
            }
        }
    }

    suspend fun fetchAllPokemons(): NetworkResult<List<Pokemon>> {
        // Realizar la llamada para obtener el listado de Pokémon
        val response = safeApiCall { pokemonService.getPokemons() }

        return when (response) {
            is NetworkResult.Success -> {
                // Extraer las URLs de los Pokémon
                val urls = response.data?.results?.map { it.url } ?: emptyList()

                // Obtener los detalles de cada Pokémon
                val pokemons = urls.mapNotNull { url ->
                    val id = url.trimEnd('/').substringAfterLast('/').toIntOrNull()
                    if (id != null) {
                        val pokemonResponse = safeApiCall { pokemonService.getPokemonById(id) }
                        when (pokemonResponse) {
                            is NetworkResult.Success -> {
                                // Mapear la respuesta a un objeto Pokemon
                                PokemonMapper.pokemonDatabaseToPokemon(pokemonResponse.data!!)
                            }
                            else -> null
                        }
                    } else null
                }

                // Devolver el resultado
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