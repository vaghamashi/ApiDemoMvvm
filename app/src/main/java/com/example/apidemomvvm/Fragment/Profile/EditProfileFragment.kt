package com.aneriservices.app.Fragment.Profile

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.ApiHelper.APIClient
import com.aneriservices.app.ApiHelper.ApiInterface
import com.aneriservices.app.Modal.Auth.updateuser
import com.aneriservices.app.Modal.enumResponseResult
import com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository.UpdateRepository
import com.aneriservices.app.Services.SharedPreferencesService
import com.aneriservices.app.Utility.CustomvalidationService
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.Utility.utilities.SnackbarService
import com.aneriservices.app.ViewModal.UpdateViewModal.UdapteViewModal
import com.aneriservices.app.ViewModal.UpdateViewModal.UpdateModalFactory
import com.aneriservices.app.databinding.FragmentEditProfileBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding
    private lateinit var sharedPreferencesService: SharedPreferencesService
    lateinit var updateviewmodal: UdapteViewModal
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var updateuser: updateuser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)

        sharedPreferencesService = SharedPreferencesService(
            requireActivity(), Utility.keyPrefs
        )
        binding.editName.setText(sharedPreferencesService.getString(Utility.keyDisplayname))
        binding.editNumber.setText(sharedPreferencesService.getString(Utility.keyNumber))
        binding.editEmial.setText(sharedPreferencesService.getString(Utility.keyEmail))
        val apiInterface = APIClient.getApiClientLogin().create(ApiInterface::class.java)
        val repository = UpdateRepository(apiInterface)

        updateviewmodal =
            ViewModelProvider(this, UpdateModalFactory(repository)).get(UdapteViewModal::class.java)

        coroutineScope.launch {
            binding.loginBtn.setOnClickListener {

                val name = binding.editName.text.toString()
                val number = binding.editNumber.text.toString()
                val email = binding.editEmial.text.toString()
                val id = sharedPreferencesService.getInt(Utility.keyid)
                if (validateLogin(name, number, email)) {
                    updateuser = updateuser(id, name, number, email)
                    updateuser.id?.let { it1 ->
                        updateviewmodal.update(
                            it1, updateuser.displayName ?: "", updateuser.mobileNo, updateuser.email
                        )
                    }

                    Utility.observeOnce(updateviewmodal.UdapteResult) { result ->
                        result.message?.let { message ->
                            if (message.contains("Timeout occurred", ignoreCase = true)) {
                                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                            } else {
                                if (result.success?.equals(enumResponseResult.success.value) == true) {
                                    sharedPreferencesService.set(Utility.keyDisplayname, name)
                                    sharedPreferencesService.set(Utility.keyNumber, number)
                                    sharedPreferencesService.set(Utility.keyEmail, email)
                                    binding.layoutWait.progebarlayout.visibility = View.VISIBLE
                                    findNavController().popBackStack()

                                    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
                                        .show()
                                } else {
                                    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
                                        .show()
                                }
                            }

                        }
                    }


                }

            }
        }

        return binding.root

    }


    fun validateLogin(name: String?, number: String?, email: String?): Boolean {
        if (name == null || name.trim().isEmpty()) {
            SnackbarService.snackbar(
                requireView(), Utility.PleaseEnterName, Snackbar.LENGTH_LONG
            )
            binding.editName.error = Utility.PleaseEnterName
            return false
        }

        if (number?.isEmpty() == true) {

            SnackbarService.snackbar(
                requireView(), Utility.PleaseEnterNumber, Snackbar.LENGTH_LONG
            )

            binding.editNumber.error = Utility.PleaseEnterNumber
            return false
        } else if (!number?.matches(CustomvalidationService.PatternContactno.toRegex())!!) {
            SnackbarService.snackbar(
                requireView(), Utility.PleaseEnterValiedNumber, Snackbar.LENGTH_LONG
            )
            binding.editNumber.error = Utility.PleaseEnterValiedNumber
            return false
        }


        if (email?.isEmpty() == true) {
            SnackbarService.snackbar(
                requireView(), Utility.PleaseEnterEmail, Snackbar.LENGTH_LONG
            )
            binding.editEmial.error = Utility.PleaseEnterEmail

            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SnackbarService.snackbar(
                requireView(), Utility.PleaseEnterValiedEmail, Snackbar.LENGTH_LONG
            )
            binding.editEmial.error = Utility.PleaseEnterValiedEmail
            return false
        }
        return true
    }
}