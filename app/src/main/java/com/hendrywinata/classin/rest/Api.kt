package com.hendrywinata.classin.rest

import com.hendrywinata.classin.data.Account
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("accounts.json")
    fun getAccounts(): Call<ArrayList<Account>>

    @FormUrlEncoded
    @POST("accounts.json")
    fun addAccount(
        @Field("id") id: String?,
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<Account>
}