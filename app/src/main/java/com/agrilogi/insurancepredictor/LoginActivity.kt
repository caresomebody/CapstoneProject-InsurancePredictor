package com.agrilogi.insurancepredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            var focusView: View? = null
            var cancel = false

            if (TextUtils.isEmpty(email)){
                binding.email.error = getString(R.string.required)
                focusView = binding.email
                cancel = true
            }

            if(TextUtils.isEmpty(password)){
                binding.password.error = getString(R.string.required)
                focusView = binding.password
                cancel = true
            }

            if (!isValidEmail(email)) {
                binding.email.error = "Invalid Email Address"
                focusView = binding.email
                cancel = true
            }

            if(cancel){
                focusView?.requestFocus()
            } else {
                if(userDB.userDao().checkUser(email, password)){
                    session.createLoginSession()
                    session.createUser(email, password)
                    toDashboard()
                } else {
                    binding.email.error = getString(R.string.didnt_match)
                    binding.password.error = getString(R.string.didnt_match)
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

    private fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}