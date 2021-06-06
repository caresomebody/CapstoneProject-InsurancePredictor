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
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm4Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm5Binding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Form5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityForm5Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        onClick()
    }

    private fun onClick() {
        binding.btnNext.setOnClickListener {
            session = SessionManagement(applicationContext)
            val email = session.user["email"]
            val user = userDB.userDao().getUserByEmail(email.toString())

            val child = binding.inputChild.text.toString()
            var focusView: View? = null

            if (TextUtils.isEmpty(child)){
                binding.inputChild.error = getString(R.string.required)
                focusView = binding.inputChild
            } else {
                insertToDb(child, user.email)
                Log.d("ini childe di form 4", user.child.toString())
//                getPredict()
                startActivity<Form6Activity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form4Activity>()
        }
    }

    private fun insertToDb(child: String, email: String) {
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateChild(child, email)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    Log.d("insertoDB", "Failed")
                }))
    }
}