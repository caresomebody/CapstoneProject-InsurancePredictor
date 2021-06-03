package com.agrilogi.insurancepredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.agrilogi.insurancepredictor.database.User
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivitySignUpBinding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class SignUpActivity : AppCompatActivity() {

    lateinit var session: SessionManagement
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userDB: UserDatabase
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        session = SessionManagement(applicationContext)
        if (session.checkLogin()) toDashboard()
        onClick()
    }

    private fun onClick() {
        binding.buttonSignUp.setOnClickListener {
            val email = binding.email.text.toString()
            val name = binding.name.text.toString()
            val password = binding.password.text.toString()
            var cancel = false
            var focusView: View? = null

            if(TextUtils.isEmpty(email)){
                binding.email.error = "Bagian ini harus diisi"
                focusView = binding.email
                cancel = true
            } else if(email == userDB.userDao().checkUserEmail(email).toString()){
                binding.email.error = "Email sudah dipakai"
                focusView = binding.email
                cancel = true
            }

            if (TextUtils.isEmpty(name)){
                binding.name.error = "Bagian ini harus diisi"
                focusView = binding.name
                cancel = true
            }

            if(TextUtils.isEmpty(password)){
                binding.password.error = "Bagian ini harus diisi"
                focusView = binding.password
                cancel = true
            }

            if(cancel){
                focusView?.requestFocus()
            } else {
                insertToDb(User(email,name,password))
                alert("Berhasil membuat akun") {
                    yesButton {
                    }
                }.show()
            }
            toast("berhasil diklik")
        }

        binding.login.setOnClickListener{
            startActivity<LoginActivity>()
        }
    }

    private fun insertToDb(user: User){
        compositeDisposable.add(Completable.fromRunnable {
            userDB.userDao().addUser(user) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{
                Log.d("insertoDB","Failed")
            }))
    }

    private fun toDashboard(){
        startActivity<MainActivity>()
        session.createOnBoardSession()
        finish()
    }
}