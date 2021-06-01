package com.agrilogi.insurancepredictor.questionnaire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.agrilogi.insurancepredictor.ResultActivity
import com.agrilogi.insurancepredictor.databinding.ActivityQuestionnaireBinding
import kotlinx.android.synthetic.main.activity_questionnaire.*
import org.jetbrains.anko.startActivity

class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireBinding
    private lateinit var pager: ViewPager
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        binding.btnNext.text = ("Next")
                        binding.btnBack.visibility = View.INVISIBLE
                        currentPage = 0
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

            if(binding.btnNext.text == ("Submit")) startActivity<ResultActivity>()
        }
        binding.btnBack.setOnClickListener {
            currentPage--
            pager.setCurrentItem(currentPage, true)
        }
    }
}