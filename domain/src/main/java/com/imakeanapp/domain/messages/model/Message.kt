package com.imakeanapp.domain.messages.model

data class Message(
        var message: String = "",
        var sender: String = "",
        var sent: Long = 0
)