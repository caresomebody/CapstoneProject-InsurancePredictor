package com.agrilogi.insurancepredictor.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.WaitingActivity
import com.agrilogi.insurancepredictor.questionnaire.QuestionnaireActivity
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityMainBinding
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        session = SessionManagement(applicationContext)
        val email = session.user["email"]
        Log.d("ini email", session.user.toString())
        val user = userDB.userDao().getUserByEmail(email.toString())

        binding.name.text = ("Hi, " + user.name)

        binding.predictNow.setOnClickListener {
            startActivity<WaitingActivity>()
        }
    }
}