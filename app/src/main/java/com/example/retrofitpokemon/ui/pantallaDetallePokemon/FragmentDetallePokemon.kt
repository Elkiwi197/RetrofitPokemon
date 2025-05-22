package com.example.retrofitpokemon.ui.pantallaDetallePokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.retrofitpokemon.databinding.FragmentDetallePokemonBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentDetallePokemon : Fragment(){
    private var _binding: FragmentDetallePokemonBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetalleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallePokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observarState()
        eventos()

        getPokemonById()
    }

    private fun getPokemonById() {
        viewModel.handleEvent(DetalleContract.PokemonEvent.LoadPokemonById(4))
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.pokemon?.let {
                binding.textViewNombre.text = state.pokemon.nombre
                binding.textViewTipos.text = state.pokemon.tipos.toString()
                binding.textViewHabilidades.text = state.pokemon.habilidades.toString()
                binding.fotoDetallePokemon.load(state.pokemon.urlFoto)
            }
        }
    }



    private fun eventos() {
//        binding.botonNavegarDetalle.setOnClickListener {
//            val action = FragmentListadoDirections.actionFragmentListadoToFragmentAnadir()
//            findNavController().navigate(action)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}