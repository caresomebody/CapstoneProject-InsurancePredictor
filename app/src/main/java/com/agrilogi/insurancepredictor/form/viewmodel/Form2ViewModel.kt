package com.agrilogi.insurancepredictor.form.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.agrilogi.insurancepredictor.ApiViewModel
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.User
import com.agrilogi.insurancepredictor.database.UserDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Form2ViewModel:ViewModel() {
    private lateinit var userDB: UserDatabase
    val compositeDisposable = CompositeDisposable()
    lateinit var session: SessionManagement

    fun getData(context: Context): User {
        userDB = UserDatabase.getInstance(context)!!
        session = SessionManagement(context)
        val email = session.user["email"]

        return userDB.userDao().getUserByEmail(email.toString())
    }

    fun insertToDb(sex: String, email: String){
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateSex(sex, email)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    Log.d("insertoDB", "Failed")
                }))
    }
}