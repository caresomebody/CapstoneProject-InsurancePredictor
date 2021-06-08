package com.agrilogi.insurancepredictor

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityResultBinding
import com.agrilogi.insurancepredictor.form.viewmodel.Form1ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.startActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var userDB: UserDatabase
    private lateinit var apiViewModel: ApiViewModel
    val compositeDisposable = CompositeDisposable()
    lateinit var session: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.result)

        val model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(Form1ViewModel::class.java)
        val user = model.getData(this)

        binding.name.text = user.name
        binding.sex.text = user.sex
        binding.age.text = user.age
        binding.smoking.text = user.smoke
        binding.bmi.text = user.bmi.toString()
        binding.children.text = user.child
        binding.location.text = user.location
        binding.charge.text = ("$" + user.price.toString())
        Log.d("ini price", user.price.toString())

        button_back_dashboard.setOnClickListener {
            startActivity<MainActivity>()
        }
        binding.buttonNext.setOnClickListener{
            startActivity<RecommendedActivity>()
        }
    }
}