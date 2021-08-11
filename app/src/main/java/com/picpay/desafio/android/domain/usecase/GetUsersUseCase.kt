package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.repository.PicPayRepository

class GetUsersUseCase(
    private val User: PicPayRepository
) {
    suspend operator fun invoke() = User.getUsers()
}