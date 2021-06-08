package com.agrilogi.insurancepredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.databinding.ActivityCalculatingBinding
import com.agrilogi.insurancepredictor.databinding.ActivityRecommendedBinding
import org.jetbrains.anko.startActivity

class CalculatingActivity : AppCompatActivity() {

    private lateinit var apiViewModel: ApiViewModel
    lateinit var session: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCalculatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiViewModel::class.java)
        apiViewModel.setPredict(this)
        binding.calculate.visibility = View.VISIBLE
        binding.progressbar.visibility = View.VISIBLE
        apiViewModel.getPredict().observe(this, {
            startActivity<RecommendedActivity>()
        })
    }
}