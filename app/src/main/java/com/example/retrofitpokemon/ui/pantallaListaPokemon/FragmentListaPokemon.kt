package com.example.retrofitpokemon.ui.pantallaListaPokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitpokemon.databinding.FragmentListaPokemonBinding
import com.example.retrofitpokemon.domain.model.Pokemon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentListaPokemon : Fragment() {
    private var _binding: FragmentListaPokemonBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PokemonAdapter
    private val viewModel: FragmentListaPokemonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        observarState()
        eventos()

        viewModel.handleEvent(ListaPokemonContract.ListaPokemonEvent.LoadPokemons)
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.listaPokemons)
        }
    }

    private fun configureRecyclerView() {
        adapter = PokemonAdapter(
            actions = object : PokemonAdapter.PokemonActions {
                override fun onItemClick(pokemon: Pokemon) {
                    val action =
                        FragmentListaPokemonDirections.actionFragmentListaPokemonToFragmentDetallePokemon(
                            pokemon.id
                        )
                    findNavController().navigate(action)
                }
            }
        )

        binding.recyclerViewPokemon.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPokemon.adapter = adapter

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