package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.*
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm5Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm6Binding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Form6Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm6Binding
    private lateinit var userDB: UserDatabase
    private lateinit var apiViewModel: ApiViewModel
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        onClick()
    }


    private fun onClick() {
        binding.btnSubmit.setOnClickListener {
            session = SessionManagement(applicationContext)
            val email = session.user["email"]
            val user = userDB.userDao().getUserByEmail(email.toString())

            val locId: Int = binding.radioGroup2.checkedRadioButtonId
            val checkedLoc = findViewById<RadioButton>(locId)
            val location = checkedLoc.text.toString().toLowerCase()
            var focusView: View? = null

            if (locId==-1){
                toast(getString(R.string.havent_chosen))
            } else {
                insertToDb(location, user.email)
                getPredict()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form5Activity>()
        }

    }

    private fun getPredict() {
        apiViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiViewModel::class.java)
        apiViewModel.setPredict(this)
        binding.calculate.visibility = View.VISIBLE
        binding.progressbar.visibility = View.VISIBLE
        apiViewModel.getPredict().observe(this, {
//            binding.calculate.visibility = View.GONE
//            binding.progressbar.visibility = View.GONE
            startActivity<RecommendedActivity>()
        })
    }

    private fun insertToDb(location: String, email: String) {
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().updateLocation(location, email)
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    Log.d("insertoDB", "Failed")
                }))
    }
}