package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.model.User

object Extensions{
    fun isNullOrEmpty(data: List<User>?): Boolean {
        return data == null || data.isNullOrEmpty()
    }
}
