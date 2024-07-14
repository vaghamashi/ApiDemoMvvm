package com.aneriservices.app.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.R
import com.aneriservices.app.Services.MyPreferences
import com.aneriservices.app.Utility.PermissionManager
import com.aneriservices.app.Utility.navigateSafe
import com.aneriservices.app.databinding.FragmentWelcomesplashscreenBinding

class WelcomesplashscreenFragment : Fragment() {

    lateinit var binding: FragmentWelcomesplashscreenBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomesplashscreenBinding.inflate(layoutInflater)
//        checkTheme()
        Handler(Looper.getMainLooper()).postDelayed({

            if (isAdded) {
                findNavController().navigateSafe(R.id.action_welcomesplashscreenFragment_to_login_Fragment)
            }


        }, 1000)


        return binding.root

    }

    private fun checkTheme() {


        when (MyPreferences(requireContext()).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }


}