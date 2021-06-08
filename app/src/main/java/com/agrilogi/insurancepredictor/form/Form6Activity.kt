package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.*
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm5Binding
import com.agrilogi.insurancepredictor.databinding.ActivityForm6Binding
import com.agrilogi.insurancepredictor.form.viewmodel.Form2ViewModel
import com.agrilogi.insurancepredictor.form.viewmodel.Form6ViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Form6Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm6Binding
    private lateinit var userDB: UserDatabase
    private lateinit var apiViewModel: ApiViewModel
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        onClick()
    }


    private fun onClick() {
        binding.btnSubmit.setOnClickListener {
            val model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(Form6ViewModel::class.java)
            val user = model.getData(this)

            val locId: Int = binding.radioGroup2.checkedRadioButtonId
            val checkedLoc = findViewById<RadioButton>(locId)
            val location = checkedLoc.text.toString().toLowerCase()
            var focusView: View? = null

            if (locId==-1){
                toast(getString(R.string.havent_chosen))
            } else {
                model.insertToDb(location, user.email)
                startActivity<CalculatingActivity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form5Activity>()
        }

    }

//    private fun getPredict() {
//        apiViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiViewModel::class.java)
//        apiViewModel.setPredict(this)
//        binding.calculate.visibility = View.VISIBLE
//        binding.progressbar.visibility = View.VISIBLE
//        apiViewModel.getPredict().observe(this, {
//            startActivity<RecommendedActivity>()
//        })
//    }

}