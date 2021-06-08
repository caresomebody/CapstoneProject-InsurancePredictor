package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm1Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm3Binding
import com.agrilogi.insurancepredictor.form.viewmodel.Form2ViewModel
import com.agrilogi.insurancepredictor.form.viewmodel.Form3ViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class Form3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm3Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        onClick()
    }

    private fun onClick() {
        binding.btnNext.setOnClickListener {
            val model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(Form3ViewModel::class.java)
            val user = model.getData(this)
            val height: String= binding.inputHeight.text.toString()
            val weight: String = binding.inputWeight.text.toString()
            var focusView: View? = null

            val heightValue = height.toFloat() / 100
            val weightValue = weight.toFloat()
            val bmi = weightValue / (heightValue * heightValue)

            if (TextUtils.isEmpty(height)){
                binding.inputHeight.error = getString(R.string.required)
                focusView = binding.inputHeight
            }

            if (TextUtils.isEmpty(weight)){
                binding.inputWeight.error = getString(R.string.required)
                focusView = binding.inputWeight
            }

            else{
                model.insertToDb(height, weight, roundNumber(bmi), user.email)
                startActivity<Form4Activity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form2Activity>()
        }
    }

    private fun roundNumber(number: Float): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toFloat()
    }
}