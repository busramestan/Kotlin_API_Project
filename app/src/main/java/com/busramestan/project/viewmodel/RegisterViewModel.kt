package com.busramestan.project.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busramestan.project.api.ProjectAPIService
import com.busramestan.project.model.UserRegister
import com.busramestan.project.model.UserResponseRegister
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class RegisterViewModel : ViewModel(){


    private val projectAPIService = ProjectAPIService()
    private val disposable= CompositeDisposable()


    val registerResponse = MutableLiveData<UserResponseRegister>()
    val registerError = MutableLiveData<Boolean>()

    fun register(register: UserRegister){
        disposable.add(
            projectAPIService.register(register)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Response<UserResponseRegister>>(){
                    override fun onSuccess(t: Response<UserResponseRegister>) {
                        registerResponse.value = t.body()

                        println("RegisterViewModel-Register-onSuccess")
                        println(t.body())
                    }

                    override fun onError(e: Throwable) {
                        registerError.value = true
                        println("RegisterViewModel-Register-onError -> ${e}")
                    }

                })
        )


    }

}