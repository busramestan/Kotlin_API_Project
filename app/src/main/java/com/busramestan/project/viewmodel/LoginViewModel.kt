package com.busramestan.project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busramestan.project.api.ProjectAPIService
import com.busramestan.project.model.UserLogin
import com.busramestan.project.model.UserRegister
import com.busramestan.project.model.UserResponseLogin
import com.busramestan.project.model.UserResponseRegister
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoginViewModel : ViewModel() {


    private val projectAPIService = ProjectAPIService()
    private val disposable = CompositeDisposable()

    val loginResponse = MutableLiveData<UserResponseLogin>()
    val loginError = MutableLiveData<Boolean>()
    val loginLoading = MutableLiveData<Boolean>()

    fun login(login: UserLogin) {

        disposable.add(
            projectAPIService.login(login)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Response<UserResponseLogin>>() {
                    override fun onSuccess(t: Response<UserResponseLogin>) {
                        loginResponse.value = t.body()
                        loginError.value = false
                        println("LoginViewModel-Login-onSuccess")
                        println(t.body())

                        t.body()?.let {
                            it.token?.let { token ->

                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        loginError.value = true
                        println("LoginViewModel-Login-onError -> ${e}")
                    }

                })
        )


    }


}
