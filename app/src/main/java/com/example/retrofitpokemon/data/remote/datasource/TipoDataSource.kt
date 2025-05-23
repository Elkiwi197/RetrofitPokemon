package com.example.retrofitpokemon.data.remote.datasource

import com.example.retrofitpokemon.common.ConstantesErrores
import com.example.retrofitpokemon.data.remote.NetworkResult
import com.example.retrofitpokemon.data.remote.apiservices.TiposService
import com.example.retrofitpokemon.di.IoDispatcher
import com.example.retrofitpokemon.domain.model.Tipo
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TipoDataSource @Inject constructor(
    private val tiposService: TiposService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun fetchTipos(): NetworkResult<List<Tipo>> {
        val llamadaTiposResponse = safeApiCall {
            tiposService.getTipos()
        }
        return when (llamadaTiposResponse) {
            is NetworkResult.Error -> NetworkResult.Error(message = llamadaTiposResponse.message)
            is NetworkResult.Loading -> NetworkResult.Loading()
            is NetworkResult.Success -> {
                val urls = llamadaTiposResponse.data?.results?.map { it.url } ?: emptyList()
                val tipos = urls.mapNotNull { url ->
                    val id = url.trimEnd('/').substringAfterLast('/').toIntOrNull()
                    if (id != null) {
                        val tipoDatabase = safeApiCall { tiposService.getTipoById(id) }
                        when (tipoDatabase) {
                            is NetworkResult.Success -> {
                                if (tipoDatabase.data != null) {
                                    if (!tipoDatabase.data?.name.equals("unknown")) {
                                        Tipo(
                                            nombreTipo = tipoDatabase.data!!.name,
                                            fotoTipo = tipoDatabase.data!!.sprites.generation_ix.scarlet_violet.name_icon
                                        )
                                    } else {
                                        null
//                                        Tipo(
//                                            nombreTipo = tipoDatabase.data!!.name,
//                                            fotoTipo = tipoDatabase.data!!.sprites.generation_iii.colosseum.name_icon
//                                            )
                                    }
                                } else {
                                    return NetworkResult.Error(message = ConstantesErrores.TIPO_VACIO)
                                }

                            }

                            else -> null
                        }
                    } else null
                }
                return NetworkResult.Success(tipos)

            }
        }
    }

}