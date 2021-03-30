package com.example.appwithapi.api


import com.example.appwithapi.models.DefaultResponse
import com.example.appwithapi.models.LoginResponse
import com.example.appwithapi.models.UsersResponse
import retrofit2.Call

import retrofit2.http.*


interface Api {

    @FormUrlEncoded
    @POST("createuser")
    fun createuser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("school") school: String,
        @Field("adress") address: String

    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("userlogin")
    fun userlogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("allusers")
    fun allusers(): Call<UsersResponse>


    @FormUrlEncoded
    @POST("updateuser/{id}")
    fun updateuser(
        @Path("id") id: Int,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("school") school: String,
        @Field("adress") address: String

    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("updateuserpassword")
    fun updateuserpassword(
        @Field("cuurent_password") cuurentPassword: String,
        @Field("new_password") newPassword: String,
        @Field("email") email: String

    ) : Call<DefaultResponse>

    @DELETE("deleteuser/{id}")
    fun deleteuser(
        @Path("id") id: Int
    ) : Call<DefaultResponse>

}