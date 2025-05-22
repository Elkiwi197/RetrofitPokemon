package com.example.retrofitpokemon.data.repositories

import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.data.remote.apiservices.PokemonService
import com.example.retrofitpokemon.data.remote.datasource.PokemonDataSource
import com.example.retrofitpokemon.data.remote.mappers.PokemonMapper
import com.example.retrofitpokemon.data.remote.model.pokemondatabase.PokemonDatabase
import com.example.retrofitpokemon.domain.model.Pokemon
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService, //todo preguntar a Oscar por que llama al service y al datasource desde la misma clase
    private val pokemonDataSource: PokemonDataSource,
) {
    suspend fun getPokemonById(id: Int): NetworkResult<Pokemon> {
        return when (val result = pokemonDataSource.fetchPokemonById(id)) {
            is NetworkResult.Success -> NetworkResult.Success(data = result.data!!)
            is NetworkResult.Error -> NetworkResult.Error(message = result.message)
            is NetworkResult.Loading -> NetworkResult.Loading()
        }
    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")


    //    suspend fun getAllPokemons(): NetworkResult<List<Pokemon>> {
//        return pokemonDataSource.fetchAllPokemons()


//    }
    // try {
//            val response = pokemonService.getPokemon(id)
//            if (response.isSuccessful) {
//                val body = response.body()
//                body?.let {
//                    return NetworkResult.Success(PokemonMapper.pokemonDatabaseToPokemon(body))
//                }
////            } else {
////                response.errorBody()
////                return error("${response.code()} ${response.message()}")
//            }
//            return error("${response.code()} ${response.message()}")
//        } catch (e: Exception) {
//            return error(e.message ?: e.toString())
//        }


}

