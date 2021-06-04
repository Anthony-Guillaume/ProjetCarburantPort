package com.example.bateaubreton.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.bateaubreton.databinding.DialogInvalidFieldBinding

class InvalidFieldDialog : DialogFragment()
{
    companion object {
        const val TAG: String = "InvalidFieldDialog"
    }
    private var _binding: DialogInvalidFieldBinding? = null
    private val binding get() = _binding!!
    var text: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        _binding = DialogInvalidFieldBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        init()
        return builder.create()
    }

    override fun onDestroy()
    {
        _binding = null
        super.onDestroy()
    }

    private fun init()
    {
        binding.textViewField.text = text
        binding.buttonDismiss.setOnClickListener { dismiss() }
    }
}
