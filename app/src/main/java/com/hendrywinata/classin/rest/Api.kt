package com.hendrywinata.classin.rest

import com.hendrywinata.classin.data.*
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

    @FormUrlEncoded
    @POST("presences/add_session.php")
    fun addCoursePresenceSession(
        @Field("course_id") course_id: String?,
        @Field("start_datetime") start_datetime: String?,
        @Field("end_datetime") end_datetime: String?,
        @Field("description") description: String?,
    ): Call<Response>

    @GET("presences/opened_list.php")
    fun getOpenedPresencesByLevelAndID(
        @Query("accID") accID: String?,
        @Query("level") level: String?
    ):Call<ArrayList<PresenceItem>>

    @GET("presences/list.php")
    fun getPresencesByCourseID(@Query("course_id") course_id: String?): Call<ArrayList<PresenceDetail>>

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

    @FormUrlEncoded
    @POST("announcements/add_announcement.php")
    fun addAnnouncement(
        @Field("course_code") course_code: String?,
        @Field("course_class") course_class: String?,
        @Field("title") title: String?,
        @Field("content") content: String?
    ): Call<Response>
}