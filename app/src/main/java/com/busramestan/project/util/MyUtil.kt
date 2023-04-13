package com.busramestan.project.util

import android.util.Patterns
import com.busramestan.project.model.User
import com.busramestan.project.model.UserData

object MyUtil {

    fun emailValidation (email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun convertListToArrayList(list:List<UserData>): ArrayList<UserData> {
        val arrayList = ArrayList<UserData>()

        for(item in list){

           arrayList.add(item)

        }

        return arrayList
    }
}