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
import java.math.RoundingMode
import java.text.DecimalFormat

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
            val height: String= binding.inputHeight.text.toString()
            val weight: String = binding.inputWeight.text.toString()
            var focusView: View? = null

            val heightValue = height.toFloat() / 100
            val weightValue = weight.toFloat()
            val bmi = weightValue / (heightValue * heightValue)

            if (TextUtils.isEmpty(height)){
                binding.inputHeight.error = "Bagian ini harus diisi"
                focusView = binding.inputHeight
            }

            if (TextUtils.isEmpty(weight)){
                binding.inputWeight.error = "Bagian ini harus diisi"
                focusView = binding.inputWeight
            }

            else{
                insertToDb(height, weight, roundNumber(bmi), user.email)
                startActivity<Form3Activity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form1Activity>()
        }
    }

    private fun insertToDb(height: String, weight: String, bmi: Float, email: String){
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateBMI(height, weight, bmi, email)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Log.d("insertoDB", "Failed")
            }))
    }

    private fun roundNumber(number: Float): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toFloat()
    }


}