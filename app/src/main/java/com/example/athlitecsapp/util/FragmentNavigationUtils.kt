package com.example.athlitecsapp.util

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.athlitecsapp.R
object FragmentNavigationUtils {

    fun navigateWithAnimation(
        destinationId: Int,
        navController: NavController,
        bundle: Bundle? = null // Add optional bundle parameter
    ) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)  // Enter animation for new fragment
            .setExitAnim(R.anim.slide_out_left)   // Exit animation for current fragment
            .setPopExitAnim(R.anim.slide_out_right) // Exit animation when back is pressed
            .build()

        navController.navigate(destinationId, bundle, navOptions) // Pass bundle here
    }
}
