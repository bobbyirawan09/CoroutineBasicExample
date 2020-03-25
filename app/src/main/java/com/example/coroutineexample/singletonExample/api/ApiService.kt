package com.example.coroutineexample.singletonExample.api

import com.example.coroutineexample.singletonExample.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    suspend fun getUserId(
        @Path("userId") userId: String
    ): User

}