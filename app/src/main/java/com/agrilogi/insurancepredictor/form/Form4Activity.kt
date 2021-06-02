package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agrilogi.insurancepredictor.databinding.ActivityForm3Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm4Binding

class Form4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm4Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}