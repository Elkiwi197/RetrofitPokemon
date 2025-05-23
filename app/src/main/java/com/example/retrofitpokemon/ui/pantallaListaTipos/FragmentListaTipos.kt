package com.example.retrofitpokemon.ui.pantallaListaTipos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitpokemon.databinding.FragmentListaTiposBinding
import com.example.retrofitpokemon.ui.common.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentListaTipos : Fragment() {
    private var _binding: FragmentListaTiposBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TipoAdapter
    private val viewModel: FragmentListaTiposViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaTiposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        observarState()

        viewModel.handleEvent(ListaTiposContract.ListaTipoEvent.LoadTipos)
    }

    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.let {
                adapter.submitList(state.listaTipos)
                state.uiEvent?.let {
                    when (state.uiEvent) {
                        is UiEvent.ShowSnackbar -> Toast.makeText(
                            requireContext(),
                            state.uiEvent.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                if (state.isLoading) {
                    binding.progressBarTipos.visibility = View.VISIBLE
                } else {
                    binding.progressBarTipos.visibility = View.GONE
                }
            }
        }
    }

    private fun configureRecyclerView() {
        adapter = TipoAdapter()
        binding.listaTipos.layoutManager = LinearLayoutManager(requireContext())
        binding.listaTipos.adapter = adapter

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
