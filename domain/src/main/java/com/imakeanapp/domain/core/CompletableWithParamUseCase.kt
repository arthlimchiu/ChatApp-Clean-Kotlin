package com.imakeanapp.domain.core

import io.reactivex.Completable


interface CompletableWithParamUseCase<in T> {

    fun execute(t: T): Completable
}