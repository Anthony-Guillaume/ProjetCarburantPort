package com.example.bateaubreton.view.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bateaubreton.R
import com.example.bateaubreton.databinding.FragmentAuthenticationBinding
import com.example.bateaubreton.view.utils.CheckHelper
import com.example.bateaubreton.viewModel.AuthenticationViewModel
import com.example.bateaubreton.viewModel.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {
    companion object {
        const val TAG: String = "AuthenticationFragment"
    }

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthenticationViewModel by viewModels {
        ViewModelProvider.provideAuthenticationViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAuthenticationBinding.bind(view)
        observeViewModel()
        setActionOnViewModel()
        requestLastLocation()
    }

    override fun onDestroyView()
    {
        _binding = null
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.updateCanSign(requestCode == CheckHelper.PERMISSION_GPS
                && grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLastLocation()
    {
        CheckHelper.requestGpsPermissions(this)
    }

    private fun observeViewModel()
    {
        viewModel.canSign.observe(viewLifecycleOwner) {
            binding.buttonSignIn.isEnabled = it
            binding.buttonSignUp.isEnabled = it
            if (!it)
            {
                Snackbar.make(requireView(), getString(R.string.missing_permission_explanation), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.ask_permission_button)) {
                        requestLastLocation()
                    }
                    .show()
            }
        }
    }

    private fun setActionOnViewModel()
    {
        binding.apply {
            buttonSignIn.isEnabled = false
            buttonSignUp.isEnabled = false
            buttonSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_authenticationFragment_to_signInFragment)
            }
            buttonSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_authenticationFragment_to_signUpFragment)
            }
        }
    }
}