package com.example.bateaubreton.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bateaubreton.R
import com.example.bateaubreton.databinding.FragmentPortBinding
import com.example.bateaubreton.view.adapter.PortLocationAdapter
import com.example.bateaubreton.view.dialog.EditPriceDialog
import com.example.bateaubreton.viewModel.PortViewModel
import com.example.bateaubreton.viewModel.ViewModelProvider

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
        val toolbar: Toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.create_scenario_steps_menu)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireView()).navigate(R.id.action_createScenarioStepsFragment_to_createScenarioFragment)
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.action_save -> {
                    save()
                    true
                }
                R.id.action_add_tasks_list -> {
                    addTask()
                    true
                }
                R.id.action_add_message -> {
                    addMessage()
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