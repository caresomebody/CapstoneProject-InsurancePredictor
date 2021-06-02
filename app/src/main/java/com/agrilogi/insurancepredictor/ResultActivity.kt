package com.agrilogi.insurancepredictor

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityResultBinding
import com.agrilogi.insurancepredictor.main.MainActivity
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
        supportActionBar?.hide()
        button_back_dashboard.setOnClickListener {
            startActivity<MainActivity>()
        }
        apiViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiViewModel::class.java)
        apiViewModel.setPredict(this)
        binding.calculate.visibility = View.VISIBLE
        binding.progressbar.visibility = View.VISIBLE
        apiViewModel.getPredict().observe(this, {
            Log.d("ini it", it.toString())
            binding.name.text = it.name
            binding.sex.text = it.sex
            binding.smoking.text = it.smoke
            binding.bmi.text = it.bmi.toString()
            binding.children.text = it.child
            binding.location.text = it.location
            binding.charge.text = ("$" + it.price.toString())
            Log.d("ini price", it.price.toString())
            binding.calculate.visibility = View.GONE
            binding.progressbar.visibility = View.GONE
        })

    }

//    private fun setData() {
//        userDB = UserDatabase.getInstance(this)!!
//        session = SessionManagement(applicationContext)
//        val email = session.user["email"]
//        val user = userDB.userDao().getUserByEmail(email.toString())
//        binding.calculate.visibility = View.GONE
//        binding.progressbar.visibility = View.GONE
//        binding.name.text = user.name
//        binding.sex.text = user.sex
//        binding.smoking.text = user.smoke
//        binding.bmi.text = user.bmi.toString()
//        binding.children.text = user.child
//        binding.location.text = user.location
//        binding.charge.text = ("$" + user.price.toString())
//        Log.d("ini price", user.price.toString())
//
////        apiViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(Api::class.java)
////        apiViewModel.getUserCharge(user.age, user.bmi.toString(), user.child, user.smoke, user.location, baseContext)
//    }

}