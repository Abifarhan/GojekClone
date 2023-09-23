package com.ourproject.register_module.datasource.http.usecase

import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.RegisterFeedHttpClient
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.domain.RegisterMapper
import kotlinx.coroutines.flow.Flow

class RemoteRegisterFeedLoader constructor(
    private val registerRetrofitHttpClient: RegisterFeedHttpClient
) : RegisterFeedLoader{
    override fun submit(userData: RegistrationEntity): Flow<HttpClientResult> {

        val mapper = RegisterMapper.mapRegistrationEntityToData(userData)
        return registerRetrofitHttpClient.submitRegister(mapper)
    }

}