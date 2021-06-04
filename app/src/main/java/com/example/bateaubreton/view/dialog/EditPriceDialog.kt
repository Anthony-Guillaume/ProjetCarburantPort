package com.example.bateaubreton.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.bateaubreton.R
import com.example.bateaubreton.data.entity.Fuel
import com.example.bateaubreton.databinding.DialogEditPriceBinding
import com.example.bateaubreton.viewModel.EditPriceViewModel
import com.example.bateaubreton.viewModel.ViewModelProvider

class EditPriceDialog : DialogFragment()
{
    companion object {
        const val TAG: String = "EditPriceDialog"
    }
    private var _binding: DialogEditPriceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditPriceViewModel by viewModels {
        ViewModelProvider.provideEditPriceViewModelFactory()
    }
    var fuel: Fuel? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        _binding = DialogEditPriceBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        observeViewModel()
        setActionOnViewModel()
        viewModel.initFuel(fuel!!)
        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setActionOnViewModel()
    }

    override fun onDestroy()
    {
        _binding = null
        super.onDestroy()
    }
    private fun observeViewModel()
    {
        viewModel.canValidate.observe(this) {
            binding.buttonValidate.isEnabled = it
        }
    }

    private fun setActionOnViewModel()
    {
        binding.apply {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.price_type,
                android.R.layout.simple_spinner_item ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerType.adapter = adapter
                spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
                {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long )
                    {
                        viewModel.updateType(position)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
            buttonCancel.setOnClickListener { dismiss() }
            textFieldPrice.editText?.addTextChangedListener(object : TextWatcher
            {
                override fun afterTextChanged(s: Editable?)
                {
                    s?.toString()?.let { viewModel.updatePrice(it) }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            buttonValidate.setOnClickListener {
                viewModel.validatePrice()
                dismiss()
            }
        }
    }
}
