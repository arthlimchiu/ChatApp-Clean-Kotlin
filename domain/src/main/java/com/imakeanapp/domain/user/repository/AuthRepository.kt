package com.imakeanapp.domain.user.repository

import com.imakeanapp.domain.user.model.User
import io.reactivex.Single


interface AuthRepository {

    fun signup(username: String, password: String): Single<User>

    fun login(username: String, password: String): Single<User>
}