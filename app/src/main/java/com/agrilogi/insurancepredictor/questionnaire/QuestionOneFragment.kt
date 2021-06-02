package com.agrilogi.insurancepredictor.questionnaire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agrilogi.insurancepredictor.R
import com.agrilogi.insurancepredictor.databinding.FragmentQuestionOneBinding


class QuestionOneFragment : Fragment() {

    private lateinit var binding: FragmentQuestionOneBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val age = binding.inputAge.text.toString()
    }

}