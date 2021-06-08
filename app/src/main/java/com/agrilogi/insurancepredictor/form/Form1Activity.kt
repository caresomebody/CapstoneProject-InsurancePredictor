package com.agrilogi.insurancepredictor.form

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.ApiViewModel
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm1Binding
import com.agrilogi.insurancepredictor.form.viewmodel.Form1ViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Form1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityForm1Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.btnNext.setOnClickListener {
            val model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(Form1ViewModel::class.java)
            val user = model.getData(this)
            val age = binding.inputAge.text.toString()
            var focusView: View? = null

            if (TextUtils.isEmpty(age)){
                binding.inputAge.error = getString(R.string.required)
                focusView = binding.inputAge
            } else {
                    model.insertToDb(age, user.email)
                startActivity<Form2Activity>()
            }
        }
    }

}