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

    suspend fun deletePokemon(id: Int): NetworkResult<Boolean> = withContext(Dispatchers.IO) {
        safeApiCallNoBody { pokemonService.deletePokemon(id) }
    }

    suspend fun fetchAllPokemons(): NetworkResult<List<Pokemon>> = withContext(dispatcher) {
        safeApiCall { pokemonService.getEnlaces() }
            .flatMap { result ->
                val enlaces = result.flatMap { it.pokemonEnlaces.map { pe -> pe.url } }

                coroutineScope {
                    val pokemons = enlaces.mapNotNull { enlace ->
                        async {
                            val id = enlace.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: return@async null
                            when (val r = safeApiCall { pokemonService.getPokemonById(id) }) {
                                is NetworkResult.Success -> r.data?.let { PokemonMapper.pokemonDatabaseToPokemon(it) }
                                else -> null
                            }
                        }
                    }.awaitAll().filterNotNull()

                    NetworkResult.Success(pokemons)
                }
            }
    }



//    suspend fun fetchAllPokemons(): NetworkResult<List<Pokemon>> = withContext(dispatcher) {
//        when (val listResult = safeApiCall {
//            pokemonService.getPokemonList(limit = 20, offset = 0)
//        }) {
//            is NetworkResult.Success -> {
//                val enlaces = listResult.data?.pokemonEnlaces.orEmpty()
//                if (enlaces.isEmpty()) {
//                    return@withContext NetworkResult.Error(ConstantesErrores.LLAMADA_VACIA)
//                }
//                val results = enlaces.map { enlace ->
//                    async {
//                        when (val response = safeApiCall {
//                            pokemonService.getPokemonByUrl(enlace.url)
//                        }) {
//                            is NetworkResult.Success -> response.data?.let {
//                                PokemonMapper.pokemonDatabaseToPokemon(it)
//                            }
//
//                            else -> null
//                        }
//                    }
//                }.awaitAll().filterNotNull()
//                return@withContext NetworkResult.Success(results)
//            }
//
//            is NetworkResult.Error -> NetworkResult.Error(message = listResult.message, data = null)
//            else -> NetworkResult.Error(message = ConstantesErrores.ERROR_DESCONOCIDO, data = null)
//        }
//    }


}
