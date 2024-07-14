package com.aneriservices.app.Fragment.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.DatabaseHelper.WorkingSheet.appDatabase
import com.aneriservices.app.R
import com.aneriservices.app.Services.SharedPreferencesService
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)
    lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferencesService: SharedPreferencesService
    lateinit var appdatabase: appDatabase

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        coroutineScope.launch {
////            checkTheme()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        coroutineScope.launch {

            appdatabase = appDatabase.appDB(requireContext())
            sharedPreferencesService = SharedPreferencesService(
                requireActivity(), Utility.keyPrefs
            )
            var save = sharedPreferencesService.getString(Utility.keyusertype)
            binding.displayname.setText(sharedPreferencesService.getString(Utility.keyDisplayname))
            binding.username.setText(sharedPreferencesService.getString(Utility.keyUserid))
            binding.roalname.setText(sharedPreferencesService.getString(Utility.KeyRoleName) ?: "-")
            binding.number.setText(sharedPreferencesService.getString(Utility.keyNumber))
            binding.email.setText(sharedPreferencesService.getString(Utility.keyEmail) ?: "-")


            binding.ivPermissionverification.setOnClickListener {
                when (save) {
                    "2" -> findNavController().navigate(R.id.action_adminMainFragment_to_permissionVerificationFragment)
                    "7" -> findNavController().navigate(R.id.action_teleCallerMainFragment_to_permissionVerificationFragment)
                    "9" -> findNavController().navigate(R.id.action_mainFragment_to_permissionVerificationFragment)
                    "10" -> findNavController().navigate(R.id.action_teamleaderMainFragment_to_permissionVerificationFragment)
                }
            }
            binding.icEdit.setOnClickListener {
                when (save) {
                    "2" -> findNavController().navigate(R.id.action_adminMainFragment_to_editProfileFragment)
                    "7" -> findNavController().navigate(R.id.action_teleCallerMainFragment_to_editProfileFragment)
                    "9" -> findNavController().navigate(R.id.action_mainFragment_to_editProfileFragment)
                    "10" -> findNavController().navigate(R.id.action_teamleaderMainFragment_to_editProfileFragment)
                }
            }
            binding.changepassword.setOnClickListener {
                when (save) {
                    "2" -> findNavController().navigate(R.id.action_adminMainFragment_to_changePasswordFragment)
                    "7" -> findNavController().navigate(R.id.action_teleCallerMainFragment_to_changePasswordFragment)
                    "9" -> findNavController().navigate(R.id.action_mainFragment_to_changePasswordFragment)
                    "10" -> findNavController().navigate(R.id.action_teamleaderMainFragment_to_changePasswordFragment)
                }

            }
            binding.btnSalarysliprequest.setOnClickListener {
                when (save) {

                    "2" -> findNavController().navigate(R.id.action_adminMainFragment_to_salarySlipListFragment)
                    "7" -> findNavController().navigate(R.id.action_teleCallerMainFragment_to_salarySlipListFragment)
                    "9" -> findNavController().navigate(R.id.action_mainFragment_to_salarySlipListFragment)
                    "10" -> findNavController().navigate(R.id.action_teamleaderMainFragment_to_salarySlipListFragment)
                }
            }



            binding.btnSyncData.setOnClickListener {

                when (save) {
                    2.toString() -> {
                        findNavController().navigate(R.id.action_adminMainFragment_to_syncDataFragment)
                    }

                    7.toString() -> {
                        findNavController().navigate(R.id.action_teleCallerMainFragment_to_syncDataFragment)
                    }

                    9.toString() -> {
                        findNavController().navigate(R.id.action_mainFragment_to_syncDataFragment)
                    }

                    10.toString() -> {
                        findNavController().navigate(R.id.action_teamleaderMainFragment_to_syncDataFragment)
                    }
                }
            }



            if (save.equals("7")) {
                binding.usretype.setText("Tele Caller")
            } else if (save.equals("2")) {
                binding.usretype.setText("Admin")
            } else if (save.equals("9")) {
                binding.usretype.setText("Field Executive")
            } else if (save.equals("10")) {
                binding.usretype.setText("Team Leader")
            }
            val packageManager = requireActivity().packageManager
            val packageInfo = packageManager.getPackageInfo(requireActivity().packageName, 0)
            binding.verionmae.setText("v" + packageInfo.versionName)
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController().popBackStack()
                    }
                })
//            binding.cahngeColor.setOnClickListener {
//                chooseThemeDialog()
//            }
            binding.logout.setOnClickListener {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle(Utility.LogOutDilogTitle)
                alertDialogBuilder.setMessage(Utility.LogOutDilogMessge)
                alertDialogBuilder.setPositiveButton(Utility.TextYes) { _, _ ->

                    appdatabase.allocationMaster().allDelete()
                    appdatabase.feedbackCodeMaster().deleteAll()
                    sharedPreferencesService.deleteAll()
                    when (save) {
                        "9" -> findNavController().navigate(R.id.action_mainFragment_to_login_Fragment)
                        "2" -> findNavController().navigate(R.id.action_adminMainFragment_to_login_Fragment)
                        "7" -> findNavController().navigate(R.id.action_teleCallerMainFragment_to_login_Fragment)
                        "10" -> findNavController().navigate(R.id.action_teamleaderMainFragment_to_login_Fragment)
                    }
                }
                alertDialogBuilder.setNegativeButton(Utility.TextNo) { dialog, _ ->
                    dialog.dismiss() // Dismiss the dialog
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

        }
        return binding.root
    }

//    private fun chooseThemeDialog() {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle(Utility.TextThemeDilogTitle)
//        val styles = arrayOf(Utility.DefulatTheme, Utility.LightTheme, Utility.DarkTheme)
//        val checkedItem = MyPreferences(requireContext()).darkMode
//
//        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
//            when (which) {
//                0 -> {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                    MyPreferences(requireContext()).darkMode = 0
//                }
//
//                1 -> {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    MyPreferences(requireContext()).darkMode = 1
//                }
//
//                2 -> {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    MyPreferences(requireContext()).darkMode = 2
//                }
//            }
//            dialog.dismiss()
//            requireActivity().recreate()
//        }
//        val dialog = builder.create()
//        dialog.show()
//    }

//    private fun checkTheme() {
//        when (MyPreferences(requireContext()).darkMode) {
//            0 -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//            }
//
//            1 -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//
//            2 -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            }
//        }
//    }


}