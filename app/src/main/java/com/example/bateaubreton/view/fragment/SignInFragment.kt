package com.example.bateaubreton.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bateaubreton.R
import com.example.bateaubreton.databinding.FragmentSignInBinding
import com.example.bateaubreton.viewModel.SignInViewModel
import com.example.bateaubreton.viewModel.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class SignInFragment : Fragment(R.layout.fragment_sign_in)
{
    companion object {
        const val TAG: String = "SignInFragment"
    }
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels {
        ViewModelProvider.provideSignInViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        observeViewModel()
        setActionOnViewModel()
        viewModel.fetchData()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    private fun observeViewModel()
    {
        viewModel.apply {
            localPseudo.observe(viewLifecycleOwner) {
                binding.textFieldPseudo.editText?.setText(it)
            }
            localPassword.observe(viewLifecycleOwner) {
                binding.textFieldPassword.editText?.setText(it)
            }
            rememberMe.observe(viewLifecycleOwner) {
                binding.checkBoxRememberMe.isChecked = it
            }
            userValid.observe(viewLifecycleOwner) {
                if (it)
                {
                    findNavController().navigate(R.id.action_signInFragment_to_portFragment)
                }
                else
                {
                    Snackbar.make(requireView(), getString(R.string.sign_in_invalid), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setActionOnViewModel()
    {
        binding.apply {
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
            buttonSignIn.setOnClickListener {
                viewModel.signIn()
            }
            checkBoxRememberMe.setOnClickListener {
                viewModel.updateRememberMe(binding.checkBoxRememberMe.isChecked)
            }
        }
    }
}