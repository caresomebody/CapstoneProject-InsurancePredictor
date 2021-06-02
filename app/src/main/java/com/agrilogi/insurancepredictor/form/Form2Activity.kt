package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm1Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm2Binding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity

class Form2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm2Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        onClick()
    }

    private fun onClick() {
        binding.btnNext.setOnClickListener {
            session = SessionManagement(applicationContext)
            val email = session.user["email"]
            val user = userDB.userDao().getUserByEmail(email.toString())
            val height = binding.inputHeight.text.toString()
            val weight = binding.inputWeight.text.toString()
            var focusView: View? = null

            if (TextUtils.isEmpty(height)){
                binding.inputHeight.error = "Bagian ini harus diisi"
                focusView = binding.inputHeight
            }

            if (TextUtils.isEmpty(weight)){
                binding.inputWeight.error = "Bagian ini harus diisi"
                focusView = binding.inputWeight
            }

            else{
                insertToDb(height, weight, user.email)
                startActivity<Form3Activity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form1Activity>()
        }
    }

    private fun insertToDb(height: String, weight: String, email: String){
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateBMI(height, weight, email)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Log.d("insertoDB", "Failed")
            }))
    }
}