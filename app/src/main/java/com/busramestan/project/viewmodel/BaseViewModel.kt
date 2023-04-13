package com.busramestan.project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busramestan.project.api.ProjectAPIService
import com.busramestan.project.model.EmailModel
import com.busramestan.project.model.User
import com.busramestan.project.model.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class BaseViewModel :ViewModel(){

    private val projectAPIService = ProjectAPIService()
    private val disposable= CompositeDisposable()

    val userResponse=MutableLiveData<User>()
    val userError=MutableLiveData<Boolean>()
    val userloading = MutableLiveData<Boolean>()
    val singleUserResponse=MutableLiveData<User>()

    fun getAllUser(token : String) {
        userloading.value = true
        disposable.add(
            projectAPIService.getAllUser(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Response<User>>(){
                    override fun onSuccess(t: Response<User>) {
                        println("BaseViewModel-getAllUser --> Success")
                        userloading.value = false
                        userResponse.value=t.body()
                        println(t.body())

                    }

                    override fun onError(e: Throwable) {
                        userloading.value = false
                        userError.value=true
                        println("Exception-BaseViewModel-getAllUser --> ${e}")
                    }

                })
        )
    }
    fun getLogin(token : String,email: EmailModel){
        userloading.value=true
        disposable.add(
            projectAPIService.getLogin(token, email)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Response<User>>(){
                    override fun onSuccess(t: Response<User>) {

                      singleUserResponse.value=t.body()
                    }
                    override fun onError(e: Throwable) {

                    }
                })


        )

    }

}