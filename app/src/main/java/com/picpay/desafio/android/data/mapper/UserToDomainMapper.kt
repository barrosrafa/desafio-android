package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.utils.Mapper

class UserToDomainMapper : Mapper<User, UserDomain> {

    override fun map(source: User): UserDomain {
        return UserDomain(
            img = source.img.orEmpty(),
            name = source.name.orEmpty(),
            id = source.id?:0,
            username = source.username.orEmpty()
        )
    }
}