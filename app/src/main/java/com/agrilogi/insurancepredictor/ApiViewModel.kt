package com.agrilogi.insurancepredictor

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agrilogi.insurancepredictor.database.ApiInterface
import com.agrilogi.insurancepredictor.database.User
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit


class ApiViewModel: ViewModel() {

    val listUser = MutableLiveData<User>()
    private lateinit var userDB: UserDatabase
    private lateinit var apiViewModelViewModel: ApiViewModel
    val compositeDisposable = CompositeDisposable()
    lateinit var session: SessionManagement

    fun setPredict(context: Context) {
        userDB = UserDatabase.getInstance(context)!!
        session = SessionManagement(context)
        val email = session.user["email"]
        val user = userDB.userDao().getUserByEmail(email.toString())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://asia-southeast2-insurance-cost-predictor.cloudfunctions.net/")
            .build()
        val service = retrofit.create(ApiInterface::class.java)
        val jsonObject = JSONObject()
        jsonObject.put("age", user.age)
        jsonObject.put("bmi", user.bmi)
        jsonObject.put("children", user.child)
        jsonObject.put("sex", user.sex)
        jsonObject.put("smoker", user.smoke)
        jsonObject.put("region", user.location)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val exceptionHandler = CoroutineExceptionHandler{_ , throwable->
            throwable.printStackTrace()
        }
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d("Network", "Caught $exception")
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch(handler) {

            val response = service.getCharge(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val priceJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    insertToDb(priceJson, user.email)
                    user.price = priceJson
                    listUser.postValue(user)
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    private fun insertToDb(price: String, email: String) {
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateCharge(price, email)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Log.d("insertoDB", "Failed")
            }))
    }

    fun getPredict(): LiveData<User>{
        return listUser
    }
}
