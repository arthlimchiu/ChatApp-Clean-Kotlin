package com.imakeanapp.chatappkotlin.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.imakeanapp.domain.user.model.User
import com.imakeanapp.domain.user.usecase.LoginUseCase
import com.imakeanapp.domain.user.usecase.SignUpUseCase

class AuthViewModel(private val signUp: SignUpUseCase,
                    private val login: LoginUseCase) : ViewModel() {

    fun signUp(username: String, password: String) = signUp.execute(User(username, password))

    fun login(username: String, password: String) = login.execute(User(username, password))
}