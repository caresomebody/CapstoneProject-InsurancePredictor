package com.agrilogi.insurancepredictor.questionnaire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.agrilogi.insurancepredictor.ResultActivity
import com.agrilogi.insurancepredictor.database.User
import com.agrilogi.insurancepredictor.database.UserDatabase
import com.agrilogi.insurancepredictor.databinding.ActivityQuestionnaireBinding
import com.agrilogi.insurancepredictor.databinding.FragmentQuestionOneBinding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_questionnaire.*
import org.jetbrains.anko.startActivity

class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireBinding
    private lateinit var pager: ViewPager
    private lateinit var userDB: UserDatabase
    val compositeDisposable = CompositeDisposable()
    private var currentPage = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDatabase.getInstance(this)!!

        pager = container_quest
        pager.adapter = QuestionnairePageAdapter(supportFragmentManager)
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        val questionOneBinding = FragmentQuestionOneBinding.inflate(layoutInflater)
                        val age = questionOneBinding.inputAge.text.toString()

                        binding.btnNext.text = ("Next")
                        binding.btnBack.visibility = View.INVISIBLE
                        currentPage = 0
                        binding.btnNext.setOnClickListener {
                            Log.d("ini age", age)
                        }
                    }
                    1 -> {
                        binding.btnNext.text = ("Next")
                        binding.btnBack.visibility = View.VISIBLE
                        currentPage = 1
                    }
                    2 -> {
                        binding.btnNext.text = ("Next")
                        binding.btnBack.visibility = View.VISIBLE
                        currentPage = 2
                    }
                    3 -> {
                        binding.btnNext.text = ("Submit")
                        binding.btnBack.visibility = View.VISIBLE
                        currentPage = 3
                    }
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (currentPage < 3) {
                currentPage++
                pager.setCurrentItem(currentPage, true)
            }
            if(binding.btnNext.text == ("Submit")) {
                startActivity<ResultActivity>()
            }

        }
        binding.btnBack.setOnClickListener {
            currentPage--
            pager.setCurrentItem(currentPage, true)
        }
    }

//    private fun insertToDb(user: User){
//        compositeDisposable.add(Completable.fromRunnable {
//            userDB.userDao().updateUser(user) }
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//
//                },{
//                    Log.d("insertoDB","Failed")
//                }))
//    }
}