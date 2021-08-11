package com.picpay.desafio.android.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.presentation.model.UserPresentation
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun update(UserItem: UserPresentation) {
        binding.name.text = UserItem.name
        binding.username.text = UserItem.username

        Picasso.get()
            .load(UserItem.img)
            .into(binding.picture)
    }
}