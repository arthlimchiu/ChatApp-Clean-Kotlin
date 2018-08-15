package com.imakeanapp.data.core

import com.imakeanapp.data.messages.MessagesRepositoryImpl
import com.imakeanapp.data.user.AuthRepositoryImpl
import com.imakeanapp.domain.messages.repository.MessagesRepository
import com.imakeanapp.domain.user.repository.AuthRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesAuthRepository(repository: AuthRepositoryImpl): AuthRepository {
        return repository
    }

    @Provides
    fun providesMessagesRepository(repository: MessagesRepositoryImpl): MessagesRepository {
        return repository
    }
}