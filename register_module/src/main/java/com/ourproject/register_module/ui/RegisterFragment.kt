package com.ourproject.register_module.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ourproject.feature_dashboard.DashboardActivity
import com.ourproject.register_module.databinding.FragmentRegisterBinding
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.presentation.RegisterFeedViewModel
import com.ourproject.register_module.presentation.RegisterViewModelFactory


class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterFeedViewModel
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


            inputName.apply {
                setHint("Isi Nama Anda")
            }
        }
        val registrationData = RegistrationEntity(
            name = "alfonso3",
            email = "fonso55@gmail.com",
            password = "1234567890",
            password_confirmation = "1234567890",
            address = "Jalan berkah",
            city = "Lhokseumawe",
            houseNumber = "1",
            phoneNumber = "1"
        )


        viewModel = ViewModelProvider(this, RegisterViewModelFactory.FACTORY).get(RegisterFeedViewModel::class.java)


        viewModel.submitUserRegister(registrationData)

        viewModel.fetchUserDataLocal(requireContext())

        viewModel.userDataLiveData.observe(viewLifecycleOwner){userData ->

            if (userData != null){
                val intent = Intent(requireActivity(), DashboardActivity::class.java)
                startActivity(intent)
            } else {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}