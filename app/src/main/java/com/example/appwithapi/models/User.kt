package com.example.appwithapi.models

class User(id: Int, email: String, name: String, school: String, adress: String){

    var id: Int? = null
    var email: String? = null
    var name: String? = null
    var password: String? = null
    var school: String? = null
    var adress: String? = null

    init {
        this.id = id
        this.email = email
        this.name = name
      //  this.password = password
        this.school = school
        this.adress = adress
    }

    fun get_id (): Int ?{
        return id
    }

    fun get_email (): String? {
        return email
    }

    fun get_name (): String? {
        return name
    }
//
//    fun get_password (): String? {
//        return password
//    }

    fun get_school (): String? {
        return school
    }

    fun get_adress (): String? {
        return adress
    }

}