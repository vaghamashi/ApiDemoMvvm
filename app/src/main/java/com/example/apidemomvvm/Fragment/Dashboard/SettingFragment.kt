package com.aneriservices.app.Fragment.Dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.R
import com.aneriservices.app.Services.MyPreferences
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.databinding.FragmentSettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingFragment : Fragment() {


    lateinit var binding: FragmentSettingBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        Utility.hideKeyboard(requireActivity())
        coroutineScope.launch {
//            checkTheme()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            })

        val packageManager = requireActivity().packageManager
        val packageInfo = packageManager.getPackageInfo(requireActivity().packageName, 0)
        binding.verionmae.setText("v" + packageInfo.versionName)


        binding.ivPermissionverification.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_permissionVerificationFragment)


        }

        binding.cahngeColor.setOnClickListener {
            chooseThemeDialog()
        }
        return binding.root
    }


    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(Utility.TextThemeDilogTitle)
        val styles = arrayOf(Utility.DefulatTheme, Utility.LightTheme, Utility.DarkTheme)
        val checkedItem = MyPreferences(requireContext()).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(requireContext()).darkMode = 0
                }

                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(requireContext()).darkMode = 1
                }

                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(requireContext()).darkMode = 2
                }
            }
            dialog.dismiss()
            requireActivity().recreate()
        }
        val dialog = builder.create()
        dialog.show()
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