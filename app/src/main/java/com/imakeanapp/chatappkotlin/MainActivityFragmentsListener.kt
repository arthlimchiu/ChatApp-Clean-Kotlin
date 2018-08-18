package com.imakeanapp.chatappkotlin


interface MainActivityFragmentsListener {

    fun onLoginClick()

    fun onSignUpClick()

    fun onLogoutClick()

    fun onLoginSuccess(username: String)

    fun onSignUpSuccess(username: String)
}