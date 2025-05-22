package com.example.retrofitpokemon.ui.pantallaListaPokemon

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpokemon.databinding.PokemonViewBinding
import com.example.retrofitpokemon.domain.model.Pokemon

class PokemonViewHolder(
    itemView: View,
    val actions: PokemonAdapter.PokemonActions
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = PokemonViewBinding.bind(itemView)

    fun bind(pokemon: Pokemon) {
        with(binding) {
            textViewNombre.text = pokemon.nombre
            textViewTipo.text = pokemon.tipos.toString()

            itemView.setOnLongClickListener {
                true
            }
            itemView.setOnClickListener {
                actions.onItemClick(pokemon)
            }
        }
    }
}