package com.example.retrofitpokemon.ui.pantallaDetallePokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.retrofitpokemon.databinding.FragmentDetallePokemonBinding
import com.example.retrofitpokemon.ui.common.UiEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentDetallePokemon : Fragment() {
    private var _binding: FragmentDetallePokemonBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetalleViewModel by viewModels()
    private val args: FragmentDetallePokemonArgs by navArgs()

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
        getPokemonById(args.idPokemon)
    }

    private fun getPokemonById(id: Int) {
        viewModel.handleEvent(DetalleContract.PokemonEvent.LoadPokemonById(id))
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.let {
                state.pokemon?.let {
                    binding.textViewNombre.text = state.pokemon.nombre
                    binding.textViewTipos.text = state.pokemon.tipos.toString()
                    binding.textViewHabilidades.text = state.pokemon.habilidades.toString()
                    binding.fotoDetallePokemon.load(state.pokemon.urlFoto)
                }
                if (state.isLoading) {
                    binding.progressBar.visibility = (View.VISIBLE)
                } else {
                    binding.progressBar.visibility = (View.GONE)
                }
                state.uiEvent?.let {
                    when (state.uiEvent) {
                        is UiEvent.ShowSnackbar -> Toast.makeText(
                            requireContext(),
                            state.uiEvent.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}