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
import java.util.*

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
        binding.btnNext.setOnClickListener {
            session = SessionManagement(applicationContext)
            val email = session.user["email"]
            val user = userDB.userDao().getUserByEmail(email.toString())
            val smokeId: Int = binding.radioGroup.checkedRadioButtonId
            val checkedSmoke= findViewById<RadioButton>(smokeId)
            val smoke = checkedSmoke.text.toString().toLowerCase(Locale.ROOT)

            if (smokeId==-1){
                toast(getString(R.string.havent_chosen))
            } else {
                insertToDb(smoke, user.email)
                startActivity<Form5Activity>()
            }
        }
        binding.btnBack.setOnClickListener {
            startActivity<Form3Activity>()
        }
    }

    private fun insertToDb(smoke: String, email: String){
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateSmoke(smoke, email)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    Log.d("insertoDB", "Failed")
                }))
    }
}