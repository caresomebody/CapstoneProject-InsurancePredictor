package com.agrilogi.insurancepredictor

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityRecommendedBinding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.startActivity

class RecommendedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecommendedBinding
    private lateinit var userDB: UserDatabase
    private lateinit var apiViewModel: ApiViewModel
    val compositeDisposable = CompositeDisposable()
    lateinit var session: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        session = SessionManagement(applicationContext)
        val email = session.user["email"]
        val user = userDB.userDao().getUserByEmail(email.toString())
        binding.charge.text = ("$" + user.price.toString())
        onResult()
        onClickUrl()
    }

    private fun onResult() {
        binding.btnBackDashboard.setOnClickListener {
            startActivity<MainActivity>()
        }
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