package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.presentation.mapper.PicPayPresentation

interface PicPayRepository {
    suspend fun getUsers(): PicPayPresentation
}