package com.example.appwithapi.models

import com.google.gson.annotations.SerializedName

abstract class DefaultResponse (err: Boolean, msg: String) {

    @SerializedName("error")  // for using a different name (json and variables)
    var c_err : Boolean? = null

    @SerializedName("message")
    var c_msg : String? = null

    init {
        c_err = err
        c_msg = msg
    }

    fun isErr () : Boolean? {
        return c_err
    }

    fun getMsg () : String? {
        return c_msg
    }

}