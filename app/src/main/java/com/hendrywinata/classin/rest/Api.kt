package com.hendrywinata.classin.rest

import com.hendrywinata.classin.data.Account
import com.hendrywinata.classin.data.CourseItem
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("accounts/login.php")
    fun loginAccount(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<Account>

    @GET("accounts/detail.php")
    fun getAccountDetailByID(@Query("id") id: String?): Call<Account>

    @GET("courses/active_list.php")
    fun getActiveCoursesByLevelAndID(
        @Query("accID") accID: String?,
        @Query("level") level: String?
    ):Call<ArrayList<CourseItem>>

//    TODO : not used yet, might be deleted

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