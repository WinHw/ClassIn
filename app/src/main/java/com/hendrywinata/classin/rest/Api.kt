package com.hendrywinata.classin.rest

import com.hendrywinata.classin.data.Account
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("accounts/.json")
    fun getAccounts(): Call<ArrayList<Account>>
}