package com.example.bateaubreton.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bateaubreton.R
import com.example.bateaubreton.databinding.FragmentSignUpBinding
import com.example.bateaubreton.viewModel.SignUpViewModel
import com.example.bateaubreton.viewModel.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : Fragment(R.layout.fragment_sign_up)
{
    companion object {
        const val TAG: String = "SignUpFragment"
    }
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels {
        ViewModelProvider.provideSignUpViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        observeViewModel()
        setActionOnViewModel()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun observeViewModel()
    {
        viewModel.userValid.observe(viewLifecycleOwner) {
            if (it)
            {
                Snackbar.make(requireView(), getString(R.string.sign_up_succeed), Snackbar.LENGTH_SHORT)
                    .show()
            }
            else
            {
                Snackbar.make(requireView(), getString(R.string.sign_up_invalid), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setActionOnViewModel()
    {
        binding.apply {
            textFieldEmail.editText?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?)
                {
                    s?.toString()?.let { viewModel.updateEmail(it) }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            textFieldPseudo.editText?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?)
                {
                    s?.toString()?.let { viewModel.updatePseudo(it) }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            textFieldPassword.editText?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?)
                {
                    s?.toString()?.let { viewModel.updatePassword(it) }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            buttonSignUp.setOnClickListener {
                viewModel.signUp()
            }
        }

    }
}