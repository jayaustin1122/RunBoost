package com.example.athlitecsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.athlitecsapp.R
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.databinding.FragmentSignUpBinding
import com.example.athlitecsapp.databinding.FragmentSplashBinding
import com.example.athlitecsapp.table.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDao: UserDao
    private lateinit var database: RunBoostDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = RunBoostDatabase(requireContext())
        userDao = database.getUserDao()
        binding.apply {
            loginButton.setOnClickListener {
                validateInputs()
            }
        }
    }

    private fun validateInputs() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                binding.emailEditText.error = "Email is required"
            }
            if (password.isEmpty()) {
                binding.passwordEditText.error = "Password is required"
            }
        } else {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    // Perform the database operation in the IO thread
                    userDao.insertUser(User(email = email, password = password))
                }
                Log.d("LoginFragment", "User inserted successfully: $email")
                findNavController().navigate(R.id.signInFragment)
            }
        }
    }


}