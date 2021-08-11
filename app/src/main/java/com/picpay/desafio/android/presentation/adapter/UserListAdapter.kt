package com.picpay.desafio.android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.presentation.model.UserPresentation

class UserListAdapter : ListAdapter<UserPresentation, UserListItemViewHolder>(UserDiff) {

    private var repositories: List<UserPresentation> = emptyList()
    private lateinit var viewBinding: ListItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        viewBinding = ListItemUserBinding.inflate(inflater)

        return UserListItemViewHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.update(repositories[position])
    }

    fun update(repos: List<UserPresentation>) {
        repositories = emptyList()
        repositories = repos
        submitList(repositories)
    }

    override fun getItemCount(): Int = repositories.size
}