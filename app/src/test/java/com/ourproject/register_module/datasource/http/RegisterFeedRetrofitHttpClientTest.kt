package com.ourproject.register_module.datasource.http

import app.cash.turbine.test
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import io.mockk.CapturingSlot
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.verifyOrder
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test




class User {
    fun register(registrationDto: RegistrationDto): Boolean {
        // Your registration logic here
        return true
    }
}

class RegisterFeedRetrofitHttpClientTest {

    private val service = mockk<RegisterUserService>()
    private lateinit var sut: RegisterFeedRetrofitHttpClient
    var userData = mockk<RegistrationDto>()




    @Before
    fun setUp() {
        sut = RegisterFeedRetrofitHttpClient(registerUserService = service)
//        mockkObject(userMocked)
        every  {
//            userMocked.add(RegistrationDto(
//                name = "birin",
//                email = "birin1@gmail.com",
//                password = "1234567890",
//                password_confirmation = "1234567890",
//                address = "Jakarta Pusat",
//                city = "Jakarta",
//                houseNumber = "3",
//                phoneNumber = "5"
//
//            ))

            userData
        }
    }

    @Test
    fun testSubmitData() {
//        println("here the user data user ${userMocked}")
    }

    @Test
    fun testGetFailsOnConnectivityError() = runBlocking {
        expect(
            sut = sut,
            expectedResult = ConnectivityException()
        )
    }


    private fun expect(
        withStatusCode: Int? = null,
        sut: RegisterFeedRetrofitHttpClient,
        receivedResult: Any? = null,
        expectedResult: Any,
        submitDataUser: RegistrationDto? = null
    ) = runBlocking {

        println("here the result you get $expectedResult")



        when{

//            expectedResult is HttpClientResult.Success -> {
//                coEvery {
//                    service.get()
//                }
//            }
        }

        sut.submitRegister(
            submit = RegistrationDto(
                name = "birin",
                email = "birin1@gmail.com",
                password = "1234567890",
                password_confirmation = "1234567890",
                address = "Jakarta Pusat",
                city = "Jakarta",
                houseNumber = "3",
                phoneNumber = "5"

            )
        ).test {

        }
    }


    @Test
    fun testValidData() = runBlocking{
        val user = mockk<User>()

//        val registrationDtoSlot: CapturingSlot<RegistrationDto> = slot()
//        every {
//            user.register(capture(registrationDtoSlot))
//        } answers {
//            println("RegistrationDto: ${registrationDtoSlot.captured}")
//            true
//        }

        val registrationDto = RegistrationDto(
            name = "birin",
            email = "birin1@gmail.com",
            password = "1234567890",
            password_confirmation = "1234567890",
            address = "Jakarta Pusat",
            city = "Jakarta",
            houseNumber = "3",
            phoneNumber = "5"

        )

//        val result = user.register(registrationDto)
//
////        assert(result) {"Registration success"}
//
//        verifyOrder {
//            user.register(registrationDto)
//        }


//        confirmVerified(user)


        sut.submitRegister(submit = registrationDto).test {
            when (val receivedResult = awaitItem()){
                is HttpClientResult.Success -> {
                    println("register success")
                }
                is HttpClientResult.Failure -> {
                    println("Register gagal")
                }
            }
            awaitComplete()

        }

//        coVerify(exactly = 1){
//            service
//        }

        confirmVerified(service)
    }
}

