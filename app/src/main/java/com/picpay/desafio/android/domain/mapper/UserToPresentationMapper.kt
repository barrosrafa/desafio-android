package com.picpay.desafio.android.domain.mapper

import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.presentation.mapper.PicPayPresentation
import com.picpay.desafio.android.presentation.model.UserPresentation
import com.picpay.desafio.android.utils.Mapper

class UserToPresentationMapper : Mapper<List<UserDomain>?, PicPayPresentation> {

    override fun map(source: List<UserDomain>?): PicPayPresentation {
        return when {
            source == null -> {
                PicPayPresentation.ErrorResponse
            }
            source.isEmpty() -> {
                PicPayPresentation.EmptyResponse
            }
            else -> {
                toPresentation(source)
            }
        }
    }

    private fun toPresentation(source: List<UserDomain>): PicPayPresentation {
        return PicPayPresentation.SuccessResponse(
            source.map {
                UserPresentation(
                    img = it.img,
                    name = it.name,
                    id = it.id,
                    username = it.username
                )
            }
        )
    }
}