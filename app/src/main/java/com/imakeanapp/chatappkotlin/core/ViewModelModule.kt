package com.imakeanapp.chatappkotlin.core

import com.imakeanapp.chatappkotlin.auth.viewmodel.AuthViewModelFactory
import com.imakeanapp.data.user.AuthRepositoryImpl
import com.imakeanapp.domain.user.usecase.LoginUseCase
import com.imakeanapp.domain.user.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesAuthViewModelFactory(repository: AuthRepositoryImpl): AuthViewModelFactory {
        return AuthViewModelFactory(
                SignUpUseCase(repository),
                LoginUseCase(repository)
        )
    }
}