package com.example.retrofitpokemon.ui.pantallaListaTipos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.retrofitpokemon.R
import com.example.retrofitpokemon.domain.model.Tipo

class TipoAdapter(
) : ListAdapter<Tipo, ListaTiposViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaTiposViewHolder {
        return ListaTiposViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListaTiposViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<Tipo>() {
        override fun areItemsTheSame(oldItem: Tipo, newItem: Tipo): Boolean {
            return oldItem.nombreTipo == newItem.nombreTipo
        }

        override fun areContentsTheSame(oldItem: Tipo, newItem: Tipo): Boolean {
            return oldItem == newItem
        }
    }
}
