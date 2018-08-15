package com.imakeanapp.domain.core

import io.reactivex.Completable


interface CompletableUseCase {

    fun execute(): Completable
}