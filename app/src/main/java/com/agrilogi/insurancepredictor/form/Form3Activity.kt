package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm1Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm3Binding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class Form3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm3Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm3Binding.inflate(layoutInflater)
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
                toast("Anda belum memilih apapun")
            } else {
                insertToDb(smoke, user.email)
                startActivity<Form4Activity>()
            }
        }
        binding.btnBack.setOnClickListener {
            startActivity<Form2Activity>()
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