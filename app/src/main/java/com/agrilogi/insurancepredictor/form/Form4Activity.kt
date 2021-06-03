package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.ResultActivity
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.ApiInterface
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm4Binding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Retrofit

class Form4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm4Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        onClick()
    }

    private fun onClick() {
        binding.btnSubmit.setOnClickListener {
            session = SessionManagement(applicationContext)
            val email = session.user["email"]
            val user = userDB.userDao().getUserByEmail(email.toString())

            val child = binding.inputChild.text.toString()
            val locId: Int = binding.radioGroup2.checkedRadioButtonId
            val checkedLoc = findViewById<RadioButton>(locId)
            val location = checkedLoc.text.toString().toLowerCase()
            var focusView: View? = null

            if (TextUtils.isEmpty(child)){
                binding.inputChild.error = getString(R.string.required)
                focusView = binding.inputChild
            }

            if (locId==-1){
                toast(getString(R.string.havent_chosen))
            } else {
                insertToDb(child, location, user.email)
                Log.d("ini childe di form 4", user.child.toString())
//                getPredict()
                startActivity<ResultActivity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form3Activity>()
        }
    }
//
//    fun getPredict() {
//        userDB = UserDatabase.getInstance(this)!!
//        session = SessionManagement(applicationContext)
//        val email = session.user["email"]
//        val user = userDB.userDao().getUserByEmail(email.toString())
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://asia-southeast2-insurance-cost-predictor.cloudfunctions.net/")
//            .build()
//        val service = retrofit.create(ApiInterface::class.java)
//        val jsonObject = JSONObject()
//        jsonObject.put("age", user.age)
//        jsonObject.put("bmi", user.bmi)
//        jsonObject.put("children", user.child)
//        jsonObject.put("sex", user.sex)
//        jsonObject.put("smoker", user.smoke)
//        jsonObject.put("region", user.location)
//
//        val jsonObjectString = jsonObject.toString()
//        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//
//        CoroutineScope(Dispatchers.IO).launch {
//            // Do the POST request and get response
//            val response = service.getCharge(requestBody)
//
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//
//                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(
//                        JsonParser.parseString(
//                            response.body()
//                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
//                        )
//                    )
//                    insertCharge(prettyJson, user.email)
//                    Log.d("Pretty Printed JSON :", prettyJson)
//                } else {
//
//                    Log.e("RETROFIT_ERROR", response.code().toString())
//
//                }
//            }
//        }
//    }
//
//    private fun insertCharge(prettyJson: String, email: String) {
//        compositeDisposable.add(Completable.fromRunnable {
//            userDB.userDao().updateCharge(prettyJson, email)
//        }
//            .subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//
//            }, {
//                Log.d("insertoDB", "Failed")
//            }))
//    }

    private fun insertToDb(child: String, location: String, email: String) {
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateChill(child, location, email)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Log.d("insertoDB", "Failed")
            }))
    }
}