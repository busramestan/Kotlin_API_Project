package com.busramestan.project.api

import com.busramestan.project.model.*
import com.busramestan.project.util.Constant
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ProjectAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ProjectAPI::class.java)

    fun login(userLogin:UserLogin) : Single<Response<UserResponseLogin>> {

        return api.login(userLogin)

    }
    fun register(userRegister: UserRegister): Single<Response<UserResponseRegister>>{
        return api.register(userRegister)

    }

    fun getAllUser(token : String): Single<Response<User>> {

        return api.getAllUsers(token)

    }

    fun getLogin (token: String ,email : EmailModel) : Single<Response<User>> {

         return api.getLogin(token,email)
    }
}

