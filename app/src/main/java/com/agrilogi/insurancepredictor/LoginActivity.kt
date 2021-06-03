package com.agrilogi.insurancepredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityLoginBinding
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {
    lateinit var session: SessionManagement
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDB: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManagement(applicationContext)
        if (session.checkLogin()) toDashboard()
        userDB = UserDatabase.getInstance(this)!!
        OnClick()
    }

    private fun OnClick() {
        binding.buttonLogIn.setOnClickListener {
            val uname = binding.email.text.toString()
            val password = binding.password.text.toString()
            var focusView: View? = null
            var cancel = false

            if (TextUtils.isEmpty(uname)){
                binding.email.error = "Your Username has to filled!"
                focusView = binding.email
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
                if(userDB.userDao().checkUser(uname, password)){
                    session.createLoginSession()
                    session.createUser(uname, password)
                    toDashboard()
                } else {
                    binding.email.error = "Username and Password didn't match"
                    binding.password.error = "Username and Password didn't match"
                }
            }
        }

        binding.signup.setOnClickListener{
            startActivity<SignUpActivity>()
        }
    }

    private fun toDashboard(){
        startActivity<MainActivity>()
        session.createOnBoardSession()
        finish()
    }
}