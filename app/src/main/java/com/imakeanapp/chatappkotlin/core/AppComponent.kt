package com.imakeanapp.chatappkotlin.core

import com.google.firebase.firestore.FirebaseFirestore
import com.imakeanapp.data.core.DatabaseModule
import com.imakeanapp.data.core.RepositoryModule
import com.imakeanapp.domain.messages.repository.MessagesRepository
import com.imakeanapp.domain.user.repository.AuthRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    RepositoryModule::class,
    DatabaseModule::class
])
@Singleton
interface AppComponent {

    fun authRepository(): AuthRepository

    fun messagesRepository(): MessagesRepository

    fun firebaseFirestore(): FirebaseFirestore
}