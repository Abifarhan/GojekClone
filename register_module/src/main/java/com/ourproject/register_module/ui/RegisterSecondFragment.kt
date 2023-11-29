package com.ourproject.register_module.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ourproject.feature_dashboard.DashboardActivity
import com.ourproject.register_module.databinding.FragmentRegisterSecondBinding
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.presentation.RegisterFeedViewModel


class RegisterSecondFragment : Fragment() {

    private lateinit var viewModel: RegisterFeedViewModel

    private var _binding: FragmentRegisterSecondBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterSecondBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val email = arguments?.getString("email")
        val password = arguments?.getString("password")



        val data1 = arrayOf("Jakarta", "Bandung", "Tangerang")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            data1
        )

        binding.spinner.adapter = adapter


        viewModel = ViewModelProvider(this, RegisterFeedViewModel.FACTORY).get(RegisterFeedViewModel::class.java)

        viewModel.fetchUserDataLocal()
        viewModel.userDataLiveData.observe(viewLifecycleOwner){userData ->

            if (userData != null){
                val intent = Intent(requireActivity(), DashboardActivity::class.java)
                startActivity(intent)
            }
        }


        val userStatus = viewModel.isUserRegistered.value.userRegistered

        if (userStatus){
            val intent = Intent(requireActivity(), DashboardActivity::class.java)
            startActivity(intent)
        }

        binding.btnMoe.setOnClickListener {
            val phone = binding.phone.text.toString()
            val address = binding.address.text.toString()
            val houseNumber = binding.houseNumber.text.toString()
            val selectedItem = binding.spinner.selectedItem.toString()
            Toast.makeText(requireContext(), "Clicked $selectedItem", Toast.LENGTH_SHORT).show()

            viewModel.submitUserRegister(
                RegistrationEntity(
                    name = name ?: "",
                    email = email ?: "",
                    password = password ?: "",
                    password_confirmation = password ?: "",
                    address = address,
                    city = selectedItem,
                    houseNumber = houseNumber,
                    phoneNumber = phone
                )

//                RegistrationEntity(
//                    name = "hahhaa",
//                    email = "gelas26@gmail.com",
//                    password = "1234567890",
//                    password_confirmation = "1234567890",
//                    address = "berlin",
//                    city = "berlin",
//                    houseNumber = "4",
//                    phoneNumber = "1234567890"
//                )
            )
        }
    }
}