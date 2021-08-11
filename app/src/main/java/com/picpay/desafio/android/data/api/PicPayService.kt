package com.picpay.desafio.android.data.api

import com.picpay.desafio.android.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}