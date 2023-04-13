package com.busramestan.project.api

import com.busramestan.project.model.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProjectAPI {
    @POST("auth/Login")
    fun login(@Body userLogin:UserLogin): Single<Response<UserResponseLogin>>

    @POST("auth/sign-in")
    fun register(@Body userRegister: UserRegister):Single<Response<UserResponseRegister>>

    @GET("UserArchive/getAllusers")
    fun getAllUsers(@Header("Authorization") token : String) :Single<Response<User>>

    @POST("UserArchive/getuser")
    fun getLogin(@Header("Authorization") token :String,@Body emailModel: EmailModel) : Single<Response<User>>

}
