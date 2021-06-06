package com.agrilogi.insurancepredictor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
        if (user.price != null){
            binding.nameHistory.text = user.name
            binding.ChargeHistory.text = ("$ " + user.price.toString())

        } else {
            binding.cardHistory.visibility = View.GONE
            binding.notYet.visibility = View.VISIBLE
        }

        binding.predictNow.setOnClickListener {
            startActivity<WaitingActivity>()
        }

        binding.howItWorks.setOnClickListener {
            startActivity<HowActivity>()
        }

        binding.cardHistory.setOnClickListener {
            startActivity<ResultActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            session.logoutUser()
        }
        return super.onOptionsItemSelected(item)
    }
}