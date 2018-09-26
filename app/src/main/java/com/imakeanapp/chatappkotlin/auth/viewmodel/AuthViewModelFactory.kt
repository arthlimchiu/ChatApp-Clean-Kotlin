package com.imakeanapp.chatappkotlin.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imakeanapp.domain.user.usecase.LoginUseCase
import com.imakeanapp.domain.user.usecase.SignUpUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class AuthViewModelFactory @Inject constructor(
        private val signUp: SignUpUseCase,
        private val login: LoginUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(signUp, login) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}