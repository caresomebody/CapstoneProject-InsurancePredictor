package com.agrilogi.insurancepredictor.questionnaire

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class QuestionnairePageAdapter (fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> QuestionOneFragment()
            1 -> QuestionTwoFragment()
            2 -> QuestionThreeFragment()
            else -> QuestionFourFragment()
        }
    }
}