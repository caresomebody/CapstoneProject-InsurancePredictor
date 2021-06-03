package com.agrilogi.insurancepredictor.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.LoginActivity
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.WaitingActivity
import com.agrilogi.insurancepredictor.*
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityMainBinding
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    private lateinit var menu: Menu
    private lateinit var apiViewModel: ApiViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        session = SessionManagement(applicationContext)
        val email = session.user["email"]
        Log.d("ini email", session.user.toString())
        val user = userDB.userDao().getUserByEmail(email.toString())

        binding.name.text = ("Hi, " + user.name)

        binding.predictNow.setOnClickListener {
            startActivity<WaitingActivity>()
        }

        binding.howItWorks.setOnClickListener {
            startActivity<HowActivity>()
        }

        apiViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiViewModel::class.java)
        apiViewModel.setPredict(this)
        apiViewModel.getPredict().observe(this, {
            if (it.price == null){
                Log.d("ini it", it.toString())
                binding.cardHistory.visibility = View.GONE
                binding.notYet.visibility = View.VISIBLE
            } else {
                Log.d("ini it", it.toString())
                binding.nameHistory.text = it.name
                binding.ChargeHistory.text = it.price
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            session.logoutUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}