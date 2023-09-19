package com.ourproject.register_module.datasource.http

import android.util.Log
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class RegisterRetrofitHttpClient constructor(
    private val registerUserService: RegisterUserService
) : GoPayRegisterLoader {
    override fun submit(userData: RegistrationData): Flow<HttpRegisterClientResult> {

        return flow {
            try {
                Log.d("TAG", "submit: submit: this part executed 1")
                val call = registerUserService.registerUser(userData)
                val response = call.execute()

                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.d("TAG", "submit: submit: this part executed 2")
                    if (responseData != null) {
                        if (responseData.meta?.code == 200) {
                            Log.d("TAG", "submit: submit: this part executed 2 $responseData")
                            emit(HttpRegisterClientResult.Success(responseData))
                        } else {
                            emit(HttpRegisterClientResult.Failure(Exception("Error: ${responseData.meta?.message}")))
                        }
                    } else {
                        emit(HttpRegisterClientResult.Failure(Exception("Response body is null")))
                    }
                } else {
                    emit(HttpRegisterClientResult.Failure(Exception("HTTP error: ${response.code()}")))
                }
            } catch (t: Throwable) {
                Log.d("TAG", "submit: submit: this part executed 3")
                emit(HttpRegisterClientResult.Failure(t))
            }
        }.flowOn(Dispatchers.IO)
//    }
//        return flow {
//            try {
//
//                emit(HttpRegisterClientResult.Success(registerUserService.registerUser(userData)))
//                val apiService = retrofit.create(RegisterUserService::class.java)
//
//                val registrationData = RegistrationData(
//                    name = "alfonso3",
//                    email = "fonso22@gmail.com",
//                    password = "1234567890",
//                    password_confirmation = "1234567890",
//                    address = "Jalan berkah",
//                    city = "Lhokseumawe",
//                    houseNumber = "1",
//                    phoneNumber = "1"
//                )
//
////                val call = apiService.registerUser(registrationData)
////
////                call.enqueue(object : Callback<ResponseData> {
////                    override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
////                        if (response.isSuccessful) {
////                            Log.d("TAG", "Registration successful. User Email: ${response.body()?.data}")
////
////                            val user = response.body()
////                            if (user != null) {
////                                Log.d("TAG", "Registration successful. User Email: ")
////                                // Registration successful, handle the user data here
////                            } else {
////                                // Handle null user data
////                            }
////                        } else {
////                            Log.d("TAG", "Registration request failed 2")
////                            // Registration request failed, handle the error here
////                        }
////                    }
////
////                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
////                        // Registration request failed, handle the error here
////                        when (t) {
////                            is java.net.UnknownHostException -> {
////                                // Handle no connectivity error
////                                Log.d("TAG", "No connectivity")
////                            }
////                            is HttpException -> {
////                                // Handle HTTP error responses (4xx and 5xx)
////                                val statusCode = t.code()
////                                when (statusCode) {
////                                    404 -> {
////                                        // Handle "Not Found" error
////                                        Log.d("TAG", "Resource not found error")
////                                    }
////                                    500 -> {
////                                        // Handle internal server error
////                                        Log.d("TAG", "Internal server error")
////                                    }
////                                    else -> {
////                                        // Handle other HTTP error codes
////                                        Log.d("TAG", "HTTP error. Status Code: $statusCode")
////                                    }
////                                }
////                            }
////                            else -> {
////                                // Handle unexpected errors
////                                Log.d("TAG", "Unexpected error: ${t.message}")
////                            }
////                        }
////                    }
////                })
//            } catch (e: Exception) {
//
//            }
//        }

    }


}