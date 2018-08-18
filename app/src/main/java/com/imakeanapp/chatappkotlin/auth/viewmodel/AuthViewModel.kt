package com.imakeanapp.chatappkotlin.auth.viewmodel

import android.arch.lifecycle.ViewModel
import com.imakeanapp.domain.user.model.User
import com.imakeanapp.domain.user.usecase.LoginUseCase
import com.imakeanapp.domain.user.usecase.SignUpUseCase
import io.reactivex.Single

class AuthViewModel(private val signUp: SignUpUseCase,
                    private val login: LoginUseCase) : ViewModel() {

    fun signUp(username: String, password: String): Single<User> {
        return signUp.execute(User(username, password))
    }

    fun login(username: String, password: String): Single<User> {
        return login.execute(User(username, password))
    }
}