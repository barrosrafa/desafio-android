package com.picpay.desafio.android.presentation.mapper

import com.picpay.desafio.android.presentation.model.UserPresentation

sealed class PicPayPresentation {
    object ErrorResponse : PicPayPresentation()
    object EmptyResponse : PicPayPresentation()
    data class SuccessResponse(val items: List<UserPresentation>) : PicPayPresentation()
}