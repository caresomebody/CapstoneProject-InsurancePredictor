package com.agrilogi.insurancepredictor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityResultBinding
import com.agrilogi.insurancepredictor.main.MainActivity
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.startActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        button_back_dashboard.setOnClickListener {
            startActivity<MainActivity>()
        }
        setData()
    }

    private fun setData() {
        userDB = UserDatabase.getInstance(this)!!
        session = SessionManagement(applicationContext)
        val email = session.user["email"]
        val user = userDB.userDao().getUserByEmail(email.toString())

        val heightStr: String = user.height.toString()
        val weightStr: String = user.weight.toString()
        val heightValue = heightStr.toFloat() / 100
        val weightValue = weightStr.toFloat()
        val bmi = weightValue / (heightValue * heightValue)

        binding.name.text = user.name
        binding.sex.text = user.sex
        binding.smoking.text = user.smoke
        binding.bmi.text = bmi.toString()
        binding.children.text = user.child
        binding.location.text = user.location

        binding.charge.text = user.price.toString()
    }

}