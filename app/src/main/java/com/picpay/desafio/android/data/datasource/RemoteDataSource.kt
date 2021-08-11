package com.picpay.desafio.android.data.datasource

import com.picpay.desafio.android.domain.model.UserDomain

interface RemoteDataSource {
    suspend fun getUsers(): List<UserDomain>?
}