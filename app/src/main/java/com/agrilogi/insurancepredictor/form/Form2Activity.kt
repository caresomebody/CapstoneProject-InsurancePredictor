package com.agrilogi.insurancepredictor.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.SessionManagement
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityForm2Binding
import com.agrilogi.insurancepredictor.form.viewmodel.Form2ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Form2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityForm2Binding
    private lateinit var userDB: UserDatabase
    lateinit var session: SessionManagement
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!
        onClick()
    }

    private fun onClick() {
        binding.btnNext.setOnClickListener {
            val model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(Form2ViewModel::class.java)
            val user = model.getData(this)
            val sexId: Int = binding.radioGroup.checkedRadioButtonId
            val checkedSex = findViewById<RadioButton>(sexId)
            val sex = checkedSex.text.toString().toLowerCase()
            var focusView: View? = null

            if (sexId==-1){
                toast(getString(R.string.havent_chosen))
            } else {
                model.insertToDb(sex, user.email)
                startActivity<Form3Activity>()
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity<Form1Activity>()
        }
    }
}