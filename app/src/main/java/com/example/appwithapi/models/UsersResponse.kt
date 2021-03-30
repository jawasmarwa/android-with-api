package com.example.appwithapi.models

class UsersResponse (error: Boolean, users: List<User>){

    var error: Boolean? = null
    var users: List<User>? = null

    init {
        this.error = error
        this.users = users
    }

}