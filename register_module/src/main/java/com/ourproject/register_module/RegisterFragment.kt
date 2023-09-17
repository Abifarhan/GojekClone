package com.ourproject.register_module

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ourproject.register_module.databinding.FragmentRegisterBinding
import com.ourproject.register_module.datasource.http.ApiService
import com.ourproject.register_module.datasource.http.ResponseData
import com.ourproject.register_module.datasource.http.User
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.retrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


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

        val apiService = retrofit.create(ApiService::class.java)

        val registrationData = RegistrationData(
            name = "alfonso3",
            email = "fonso22@gmail.com",
            password = "1234567890",
            password_confirmation = "1234567890",
            address = "Jalan berkah",
            city = "Lhokseumawe",
            houseNumber = "1",
            phoneNumber = "1"
        )

        val call = apiService.registerUser(registrationData)

//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Log.d("TAG", "onResponse: your operation is success ${response.body()}, ${response.message()}")
//                    // Registration successful, handle the response here
//                } else {
//                    Log.d("TAG", "onResponse: your operation is success 2${response.body()}")
//                    // Registration failed, handle the error here
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Log.d("TAG", "onResponse: your operation is success 3 ${t.localizedMessage}")
//                // Registration request failed, handle the error here
//            }
//        })

//        call.enqueue(object : Callback<ResponseData> {
//            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//                if (response.isSuccessful) {
//                    val responseData = response.body()
//                    if (responseData != null) {
//                        val user = responseData.data.user
//                        val accessToken = responseData.data.access_token
//                        // Now you can access the user data and access token
//                        Log.d("TAG", "User ID: ${user.id}")
//                        Log.d("TAG", "User Name: ${user.name}")
//                        Log.d("TAG", "Access Token: $accessToken")
//                        // Handle user data and access token as needed
//                    } else {
//                        // Handle null response data
//                    }
//                } else {
//                    // Registration failed, handle the error here
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                Log.d("TAG", "Registration request failed: ${t.localizedMessage}")
//                // Registration request failed, handle the error here
//            }
//        })

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    Log.d("TAG", "Registration successful. User Email: ${response.body()?.data}")

                    val user = response.body()
                    if (user != null) {
                        Log.d("TAG", "Registration successful. User Email: ")
                        // Registration successful, handle the user data here
                    } else {
                        // Handle null user data
                    }
                } else {
                    Log.d("TAG", "Registration request failed 2")
                    // Registration request failed, handle the error here
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                // Registration request failed, handle the error here
                when (t) {
                    is java.net.UnknownHostException -> {
                        // Handle no connectivity error
                        Log.d("TAG", "No connectivity")
                    }
                    is HttpException -> {
                        // Handle HTTP error responses (4xx and 5xx)
                        val statusCode = t.code()
                        when (statusCode) {
                            404 -> {
                                // Handle "Not Found" error
                                Log.d("TAG", "Resource not found error")
                            }
                            500 -> {
                                // Handle internal server error
                                Log.d("TAG", "Internal server error")
                            }
                            else -> {
                                // Handle other HTTP error codes
                                Log.d("TAG", "HTTP error. Status Code: $statusCode")
                            }
                        }
                    }
                    else -> {
                        // Handle unexpected errors
                        Log.d("TAG", "Unexpected error: ${t.message}")
                    }
                }
            }
        })



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}