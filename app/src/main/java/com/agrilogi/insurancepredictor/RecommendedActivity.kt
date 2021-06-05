package com.agrilogi.insurancepredictor

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agrilogi.insurancepredictor.databinding.ActivityRecommendedBinding

class RecommendedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecommendedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickUrl()
    }

    private fun onClickUrl() {
        binding.btnAllianz.setOnClickListener{
            goToUrl("https://www.allianzlife.com")
        }

        binding.btnManulife.setOnClickListener{
            goToUrl("https://www.manulife.com/")
        }

        binding.btnPrudential.setOnClickListener{
            goToUrl("https://www.prudential.com/")
        }

        binding.btnAig.setOnClickListener{
            goToUrl("https://www.aig.com/")
        }
    }

    private fun goToUrl(url: String) {
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }
}