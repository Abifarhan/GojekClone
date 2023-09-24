package com.ourproject.register_module.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ourproject.feature_dashboard.DashboardActivity
import com.ourproject.register_module.R
import com.ourproject.register_module.databinding.FragmentRegisterBinding
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.presentation.RegisterFeedViewModel
import com.ourproject.register_module.presentation.RegisterViewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            simpleHeader.apply {
                insertTitle = "Register page"
                insertSubtitle = "Buat akun dulu baru checkout makanan"
            }

        }


        binding.btnMoe.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("email", email)
            bundle.putString("password", password)

            val fragment = RegisterSecondFragment()
            fragment.arguments = bundle

            requireFragmentManager().beginTransaction()
                .replace(R.id.register_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}