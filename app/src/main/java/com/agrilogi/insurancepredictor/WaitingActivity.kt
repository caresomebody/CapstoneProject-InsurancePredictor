package com.agrilogi.insurancepredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agrilogi.insurancepredictor.databinding.ActivityWaitingBinding
import com.agrilogi.insurancepredictor.form.Form1Activity
import org.jetbrains.anko.startActivity

class WaitingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaitingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ready.setOnClickListener {
            startActivity<Form1Activity>()
        }
    }
}