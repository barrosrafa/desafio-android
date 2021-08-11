package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.datasource.RemoteDataSource
import com.picpay.desafio.android.domain.mapper.UserToPresentationMapper
import com.picpay.desafio.android.domain.repository.PicPayRepository
import com.picpay.desafio.android.presentation.mapper.PicPayPresentation

class PicPayRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : PicPayRepository {

    private val mapper = UserToPresentationMapper()

    override suspend fun getUsers(): PicPayPresentation {
        return mapper.map(remoteDataSource.getUsers())
    }
}