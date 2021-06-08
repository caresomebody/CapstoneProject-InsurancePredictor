package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.ResultActivity
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm4Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm5Binding
import com.agrilogi.insurancepredictor.form.viewmodel.Form2ViewModel
import com.agrilogi.insurancepredictor.form.viewmodel.Form5ViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Form5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityForm5Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.btnNext.setOnClickListener {
            val model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(Form5ViewModel::class.java)
            val user = model.getData(this)

            val child = binding.inputChild.text.toString()
            var focusView: View? = null

            if (TextUtils.isEmpty(child)){
                binding.inputChild.error = getString(R.string.required)
                focusView = binding.inputChild
            } else {
                model.insertToDb(child, user.email)
                startActivity<Form6Activity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form4Activity>()
        }
    }
}