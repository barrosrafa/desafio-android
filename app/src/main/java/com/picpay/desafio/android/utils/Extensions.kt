package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.model.User

object Extensions{
    fun isNullOrEmpty(data: List<User>?): Boolean {
        return data == null || data.isNullOrEmpty()
    }

    fun orEmpty(data: List<User>?): List<User> =
        data ?: listOf()

    fun ordinalOf(i: Int) = "$i" + if (i % 100 in 11..13) "th" else when (i % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}