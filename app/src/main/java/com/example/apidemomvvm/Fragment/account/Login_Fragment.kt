@file:Suppress("DEPRECATION")

package com.aneriservices.app.Fragment.account

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.ApiHelper.APIClient
import com.aneriservices.app.ApiHelper.ApiInterface
import com.aneriservices.app.Modal.Auth.LogInModal
import com.aneriservices.app.Modal.LoginSource
import com.aneriservices.app.Modal.enumResponseResult
import com.aneriservices.app.Modal.userType
import com.aneriservices.app.R
import com.aneriservices.app.Repository.Log_In_All_Repository.LogInRepository
import com.aneriservices.app.Services.SharedPreferencesService
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.Utility.navigateSafe
import com.aneriservices.app.Utility.utilities.SnackbarService
import com.aneriservices.app.ViewModal.LogInViewModal.LogInModalFactory
import com.aneriservices.app.ViewModal.LogInViewModal.LogInViewModal
import com.aneriservices.app.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login_Fragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var logInViewModal: LogInViewModal
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    lateinit var logInModal: LogInModal
    private lateinit var sharedPreferencesService: SharedPreferencesService
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        checkInternetConnection()
        sharedPreferencesService = SharedPreferencesService(
            requireActivity(), Utility.keyPrefs
        )

        if (isLoggedIn()) {
            val save = sharedPreferencesService.getString(Utility.keyusertype)
            when (save) {
                "9" -> findNavController().navigate(R.id.action_login_Fragment_to_mainFragment)
                "2" -> findNavController().navigateSafe(R.id.action_login_Fragment_to_adminMainFragment)
                "7" -> findNavController().navigateSafe(R.id.action_login_Fragment_to_teleCallerMainFragment)
                "10" -> findNavController().navigateSafe(R.id.action_login_Fragment_to_teamleaderMainFragment)
            }
        }

        val packageManager = requireActivity().packageManager
        val packageInfo = packageManager.getPackageInfo(requireActivity().packageName, 0)
        binding.verionmae.setText("v" + packageInfo.versionName)


        val apiInterface = APIClient.getApiClientLogin().create(ApiInterface::class.java)
        val repository = LogInRepository(apiInterface)

        logInViewModal =
            ViewModelProvider(this, LogInModalFactory(repository))[LogInViewModal::class.java]



        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO){
                binding.loginBtn.setOnClickListener {
                    val connectivityManager =
                        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

                    if (networkInfo != null && networkInfo.isConnected) {
                        val user = binding.editText.text.toString()
                        val pass = binding.etPassword.text.toString()
                        val loginSource = LoginSource.App
                        if (validateLogin(user, pass)) {
                            binding.progrebar.visibility = View.VISIBLE
                            logInModal = LogInModal(
                                user, pass, Utility.deviesid(requireContext()), loginSource.value
                            )
                            logInViewModal.login(
                                logInModal.userName,
                                logInModal.password ?: "",
                                logInModal.deviceId,
                                loginSource.value
                            )
                            logInViewModal.loginResult.removeObservers(viewLifecycleOwner)
                                logInViewModal.loginResult.observe(viewLifecycleOwner, Observer { result ->
                                    binding.progrebar.visibility = View.GONE
                                    result.message?.let { message ->
                                        snackbar?.dismiss()
                                        if (message == "Timeout occurred") {
                                            snackbar = SnackbarService.snackbarr(
                                                requireView(), message, Snackbar.LENGTH_LONG
                                            )
                                        }
                                        else if (result.success?.equals(enumResponseResult.success.value) == true) {
                                            Log.e("@@TAG", "onCreateView: "+"111" )
                                                when (result.data?.userType) {
                                                    userType.FieldExecutive.value -> {
                                                        findNavController().navigateSafe(R.id.action_login_Fragment_to_mainFragment)
                                                        sharedPreferencesService.set(
                                                            Utility.keyusertype, "9"
                                                        )
                                                        saveLoginState(true)
                                                    }

                                                    userType.Admin.value -> {
                                                        findNavController().navigateSafe(R.id.action_login_Fragment_to_adminMainFragment)
                                                        sharedPreferencesService.set(
                                                            Utility.keyusertype, "2"
                                                        )
                                                        saveLoginState(true)
                                                    }

                                                    userType.TeleCaller.value -> {
                                                        findNavController().navigateSafe(R.id.action_login_Fragment_to_teleCallerMainFragment)
                                                        sharedPreferencesService.set(
                                                            Utility.keyusertype, "7"
                                                        )
                                                        saveLoginState(true)
                                                    }

                                                    userType.TeamLeader.value -> {
                                                        findNavController().navigateSafe(R.id.action_login_Fragment_to_teamleaderMainFragment)
                                                        sharedPreferencesService.set(
                                                            Utility.keyusertype, "10"
                                                        )
                                                        saveLoginState(true)
                                                    }

                                                    else -> {
                                                        Snackbar.make(
                                                            requireView(),
                                                            Utility.UareNotValideUSer,
                                                            Snackbar.LENGTH_LONG
                                                        ).show()
                                                    }
                                                }
                                            } else {
                                            Log.e("@@TAG", "onCreateView: "+"222" )
                                            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
                                                .show()
                                            }

                                    }
                                    result.data?.let { data ->
                                        sharedPreferencesService.set(
                                            Utility.keyDisplayname, data.displayName
                                        )
                                        sharedPreferencesService.set(Utility.keyUserid, data.userName)
                                        sharedPreferencesService.set(Utility.keyNumber, data.mobileNo)
                                        sharedPreferencesService.set(Utility.keyEmail, data.email)
                                        sharedPreferencesService.set(Utility.keyid, data.id)
                                        sharedPreferencesService.set(Utility.keyEmployeeId, data.employeeId)
                                        sharedPreferencesService.set(Utility.KeyRoleName, data.roleName)
                                        binding.progrebar.visibility = View.GONE
                                    }
                                })


                        }
                    } else {
                        showNoInternetDialog()
                    }
                }
            }

        }


        return binding.root
    }

    private fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferencesService.set(Utility.keyLogin, isLoggedIn)
    }

    private fun isLoggedIn(): Boolean {
        val isLoggedIn = sharedPreferencesService.getBoolean(Utility.keyLogin, false)
        Log.d("LoginFragment", "Retrieved login state: $isLoggedIn")
        return isLoggedIn
    }

    fun validateLogin(user: String?, pass: String?): Boolean {
        if (user == null || user.trim().isEmpty()) {
            val snackbar =
                Snackbar.make(requireView(), "Please Enter User Name.", Snackbar.LENGTH_LONG)
            snackbar.show()
            return false
        }
        if (pass == null || pass.trim().isEmpty()) {
            val snackbar =
                Snackbar.make(requireView(), "Please Enter Password.", Snackbar.LENGTH_LONG)
            snackbar.show()
            return false
        }
        return true
    }

    private fun checkInternetConnection() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.INTERNET),
                REQUEST_CODE_INTERNET_PERMISSION
            )
            return
        }

        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {


        } else {

            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("No Internet Connection").setMessage("Please turn on your internet connection to use this app.")
            .setPositiveButton("Settings") { dialogInterface: DialogInterface, i: Int ->
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            }.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
            }.setCancelable(false).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_INTERNET_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkInternetConnection()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_INTERNET_PERMISSION = 100
    }

}