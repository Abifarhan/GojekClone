package com.ourproject.login_module.feed.db

interface LoginFeedLocalClient {

    suspend fun insert(email: String)

    suspend fun getUserSession() : String
}