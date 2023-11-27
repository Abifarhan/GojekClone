package com.ourproject.register_module

import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.domain.GofoodLoader
import com.ourproject.register_module.presentation.RegisterFeedViewModel
import io.mockk.MockKAnnotations
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain

class RegisterViewModelTest {

    private val useCaseRegister = spyk<RegisterFeedLoader>()
    private val useCaseResultRegister = spyk<GofoodLoader>()

    private lateinit var sut: RegisterFeedViewModel


    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        sut = RegisterFeedViewModel(goPayRegisterLoader = useCaseRegister, useCaseResultRegister)

        Dispatchers.setMain(UnconfinedTestDispatcher())
    }
}