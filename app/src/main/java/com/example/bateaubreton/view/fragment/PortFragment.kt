package com.example.bateaubreton.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bateaubreton.R
import com.example.bateaubreton.databinding.FragmentPortBinding
import com.example.bateaubreton.view.adapter.PortLocationAdapter
import com.example.bateaubreton.view.dialog.EditPriceDialog
import com.example.bateaubreton.viewModel.PortViewModel
import com.example.bateaubreton.viewModel.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar

class PortFragment : Fragment(R.layout.fragment_port)
{
    companion object {
        const val TAG: String = "PortFragment"
    }
    private var _binding: FragmentPortBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PortViewModel by viewModels {
        ViewModelProvider.provideFuelInformationViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPortBinding.bind(view)
        initToolbar()
        observeViewModel()
        setActionOnViewModel()
        viewModel.fetchData()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun initToolbar()
    {
        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.menu_fragment_port)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.action_disconnect -> {
                    findNavController().navigate(R.id.action_portFragment_to_signInFragment)
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
    }


    private fun observeViewModel()
    {
        viewModel.apply {
            binding.textFieldRadius.editText?.setText(searchRadius.value.toString())
            portLocations.observe(viewLifecycleOwner) { _ ->
                binding.recyclerViewFuel.adapter =
                    portLocations.value?.let { it ->
                        PortLocationAdapter(it) {
                            val dialog = EditPriceDialog()
                            dialog.fuel = viewModel.getFuel(it)
                            dialog.show(childFragmentManager, TAG)
                        }
                    }
                }
            currentLocation.observe(viewLifecycleOwner) {
                binding.textViewMyLatitude.text = getString(R.string.latitude_format, it.latitude)
                binding.textViewMyLongitude.text = getString(R.string.longitude_format, it.longitude)
            }
        }
    }

    private fun setActionOnViewModel()
    {
        binding.apply {
            buttonUpdatePosition.setOnClickListener {
                viewModel.updateLocation()
            }
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.distance_units,
                android.R.layout.simple_spinner_item ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerDistanceUnit.adapter = adapter
                spinnerDistanceUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
                {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long )
                    {
                        viewModel.updateDistanceUnit(position)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
            textFieldRadius.editText?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?)
                {
                    s?.toString()?.let { viewModel.updateSearchRadius(it) }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}