package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm2Binding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
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
            val sexId: Int = binding.radioGroup.checkedRadioButtonId
            val checkedSex = findViewById<RadioButton>(sexId)
            val sex = checkedSex.text.toString().toLowerCase()
            var focusView: View? = null

            if (sexId==-1){
                toast(getString(R.string.havent_chosen))
            } else {
                insertToDb(sex, user.email)
                startActivity<Form3Activity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form1Activity>()
        }
    }

    private fun insertToDb(sex: String, email: String){
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