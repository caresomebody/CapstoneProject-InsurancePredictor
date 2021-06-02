package com.agrilogi.insurancepredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agrilogi.insurancepredictor.main.MainActivity
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.startActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        supportActionBar?.hide()

        button_back_dashboard.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}