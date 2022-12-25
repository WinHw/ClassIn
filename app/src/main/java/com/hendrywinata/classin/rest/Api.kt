package com.hendrywinata.classin.rest

import com.hendrywinata.classin.data.Account
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("accounts/login.php")
    fun loginAccount(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<Account>

    @FormUrlEncoded
    @POST("accounts/id_exist.php")
    fun idExistAccount(
        @Field("id") id: String?
    ): Call<String>

    @GET("accounts/read.php")
    fun getAccounts(): Call<ArrayList<Account>>

    @FormUrlEncoded
    @POST("accounts/create.php")
    fun addAccount(
        @Field("id") id: String?,
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<Account>
}