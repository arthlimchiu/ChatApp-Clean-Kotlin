package com.imakeanapp.chatappkotlin.core

import com.imakeanapp.chatappkotlin.auth.viewmodel.AuthViewModelFactory
import com.imakeanapp.chatappkotlin.messages.viewmodel.MessagesViewModelFactory
import com.imakeanapp.data.messages.MessagesRepositoryImpl
import com.imakeanapp.data.user.AuthRepositoryImpl
import com.imakeanapp.domain.messages.usecase.GetMessagesUseCase
import com.imakeanapp.domain.messages.usecase.SendMessageUseCase
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

    @Provides
    fun providesMessagesViewModelFactory(repository: MessagesRepositoryImpl): MessagesViewModelFactory {
        return MessagesViewModelFactory(
                GetMessagesUseCase(repository),
                SendMessageUseCase(repository)
        )
    }
}