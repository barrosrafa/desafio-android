package com.picpay.desafio.android.data.datasource

import android.util.Log
import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.data.mapper.UserToDomainMapper
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.utils.Extensions

class RemoteDataSourceImpl(
    private val service: PicPayService
) : RemoteDataSource {

    private val mapper = UserToDomainMapper()

    override suspend fun getUsers(): List<UserDomain>? {
        val response = service.getUsers()

        return if (response.isSuccessful) {
            checkBody(response.body())
        } else {
            Log.d("erro no request código", response.code().toString())
            null
        }
    }

    private fun checkBody(data: List<User>?): List<UserDomain> {
        if (Extensions.isNullOrEmpty(data) || data.isNullOrEmpty()) {
            return listOf()
        } else {
            val domain: MutableList<UserDomain> = mutableListOf()
            data.forEach { (img, name, id, username) ->
                domain.add(
                    mapper.map(
                        User(
                            img, name, id, username
                        )
                    )
                )
            }

            return domain
        }
    }
}