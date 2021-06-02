package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.agrilogi.insurancepredictor.ResultActivity
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm3Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm4Binding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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
            val location = checkedLoc.text.toString()
            var focusView: View? = null

            if (TextUtils.isEmpty(child)){
                binding.inputChild.error = "Bagian ini harus diisi"
                focusView = binding.inputChild
            }

            if (locId==-1){
                toast("Anda belum memilih apapun")
            } else {
                insertToDb(child, location, user.email)
                startActivity<ResultActivity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form3Activity>()
        }
    }

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