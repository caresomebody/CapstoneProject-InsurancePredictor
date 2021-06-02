package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agrilogi.insurancepredictor.databinding.ActivityForm1Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm3Binding

class Form3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm3Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}