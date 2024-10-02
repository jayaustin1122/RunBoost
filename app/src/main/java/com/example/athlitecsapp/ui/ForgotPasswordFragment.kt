package com.example.athlitecsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.athlitecsapp.R
import com.example.athlitecsapp.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {
    private var _binding : FragmentForgotPasswordBinding? = null
            private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        val root : View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}