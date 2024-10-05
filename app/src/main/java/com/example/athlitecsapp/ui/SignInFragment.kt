package com.example.athlitecsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.athlitecsapp.R
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.databinding.FragmentSignInBinding
import com.example.athlitecsapp.table.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDao: UserDao
    private lateinit var database: RunBoostDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = RunBoostDatabase(requireContext())
        userDao = database.getUserDao()

        binding.apply {
            forgotPasswordTextView.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
            createAccount.setOnClickListener {
                findNavController().navigate(R.id.signUpFragment)
            }
            loginButton.setOnClickListener {
                val email = binding.emailEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val user = userDao.getSingleUser(email,password)

                        withContext(Dispatchers.Main) {
                            when {
                                user == null -> {

                                    Log.d("LoginFragment", "Account not registered: $email")
                                    toastMessage("Account not registered")
                                }
                                user.password != password -> {

                                    Log.d("LoginFragment", "Wrong email or password for: $email")
                                    toastMessage("Wrong email or password")
                                }
                                else -> {

                                    Log.d("LoginFragment", "User login successful: $email")
                                    findNavController().navigate(R.id.holderFragment)
                                }
                            }
                        }
                    }
                } else {
                    if (email.isEmpty()) {
                        binding.emailEditText.error = "Please enter your email"
                    }
                    if (password.isEmpty()) {
                        binding.passwordEditText.error = "Please enter your password"
                    }
                }
            }
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
