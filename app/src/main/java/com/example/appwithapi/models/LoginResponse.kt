package com.example.appwithapi.models

class LoginResponse (error: Boolean, message: String, user: User) {

    var error : Boolean? = null
    var message : String? = null
    var user : User? = null

    init {
        this.error = error
        this.message = message
        this.user = user
    }

    fun get_error() : Boolean? {
        return error
    }

    fun get_message () : String? {
        return message
    }

    fun get_user () : User? {
        return user
    }

}