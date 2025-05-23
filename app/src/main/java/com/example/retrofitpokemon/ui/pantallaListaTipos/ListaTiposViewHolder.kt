package com.example.retrofitpokemon.ui.pantallaListaTipos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.retrofitpokemon.databinding.TipoViewBinding
import com.example.retrofitpokemon.domain.model.Tipo

class ListaTiposViewHolder (itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = TipoViewBinding.bind(itemView)

    fun bind(tipo: Tipo) {
        with(binding) {
            binding.fotoTipo.load(tipo.fotoTipo)
        }
    }
}