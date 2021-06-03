package com.agrilogi.insurancepredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agrilogi.insurancepredictor.databinding.ActivityHowBinding
import org.jetbrains.anko.startActivity

class HowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.how_it_works)

        binding.predict.setOnClickListener{
            startActivity<WaitingActivity>()
        }
    }
}