package com.imakeanapp.domain.user.usecase

import com.imakeanapp.domain.core.SingleWithParamUseCase
import com.imakeanapp.domain.user.model.User
import com.imakeanapp.domain.user.repository.AuthRepository
import io.reactivex.Single

class SignUpUseCase(private val repository: AuthRepository) : SingleWithParamUseCase<User, User> {

    override fun execute(t: User) = repository.signup(t.username, t.password)
}