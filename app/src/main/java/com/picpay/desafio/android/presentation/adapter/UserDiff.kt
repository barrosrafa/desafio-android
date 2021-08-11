package com.picpay.desafio.android.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.presentation.model.UserPresentation

internal object UserDiff : DiffUtil.ItemCallback<UserPresentation>() {
    override fun areItemsTheSame(
        oldItem: UserPresentation,
        newItem: UserPresentation
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserPresentation,
        newItem: UserPresentation
    ): Boolean {
        return oldItem == newItem
    }
}