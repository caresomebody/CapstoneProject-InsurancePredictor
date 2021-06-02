package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agrilogi.insurancepredictor.databinding.ActivityForm1Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm2Binding

class Form2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}