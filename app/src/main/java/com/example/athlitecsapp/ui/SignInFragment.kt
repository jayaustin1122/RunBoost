package com.example.athlitecsapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.databinding.FragmentSignInBinding
import com.example.athlitecsapp.util.DialogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDao: UserDao
    private lateinit var database: RunBoostDatabase
    private lateinit var loadingDialog: SweetAlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        // Dismiss the dialog if it's showing when the fragment is destroyed
        if (::loadingDialog.isInitialized && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    private fun onBoardingFinish(){
            val sharedPref = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("Finished", true)
            editor.apply()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = RunBoostDatabase(requireContext())
        userDao = database.getUserDao()

        binding.apply {
            createAccount.setOnClickListener {
                findNavController().navigate(R.id.signUpFragment)
            }
            loginButton.setOnClickListener {
                val email = binding.emailEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()
                loadingDialog = DialogUtils.showLoading(requireActivity())
                loadingDialog.show()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        // Add a 2-second delay before attempting to log in
                        delay(3000)

                        val user = userDao.getSingleUser(email, password)

                        withContext(Dispatchers.Main) {
                            when {
                                user == null -> {
                                    Log.d("LoginFragment", "Account not registered: $email")
                                    toastMessage("Account not registered")
                                    loadingDialog.dismiss()
                                }
                                user.password != password -> {
                                    Log.d("LoginFragment", "Wrong email or password for: $email")
                                    toastMessage("Wrong email or password")
                                    loadingDialog.dismiss()
                                }
                                else -> {
                                    loadingDialog.dismiss()
                                    DialogUtils.showSuccessMessage(
                                        requireActivity(),
                                        "Success",
                                        "Welcome $email!"
                                    ).show()
                                    Log.d("LoginFragment", "User login successful: $email")
                                    findNavController().navigate(R.id.holderFragment)
                                    onBoardingFinish()
                                }
                            }
                        }
                    }
                } else {
                    if (email.isEmpty()) {
                        binding.emailEditText.error = "Please enter your Username"
                        loadingDialog.dismiss()
                    }
                    if (password.isEmpty()) {
                        binding.passwordEditText.error = "Please enter your password"
                        loadingDialog.dismiss()
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
