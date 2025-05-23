package com.example.retrofitpokemon.data.remote.model.tipodatabase

import com.google.gson.annotations.SerializedName


data class Sprites(
    val generation_iii: GenerationIii,
    val generation_iv: GenerationIv,
    @SerializedName("generation-ix")
    val generation_ix: GenerationIx,
    val generation_v: GenerationV,
    val generation_vi: GenerationVi,
    val generation_vii: GenerationVii,
    val generation_viii: GenerationViii
)