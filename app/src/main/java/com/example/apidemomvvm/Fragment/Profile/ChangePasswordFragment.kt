package com.aneriservices.app.Fragment.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.ApiHelper.APIClient
import com.aneriservices.app.ApiHelper.ApiInterface
import com.aneriservices.app.Modal.Auth.changePassword
import com.aneriservices.app.Modal.enumResponseResult
import com.aneriservices.app.R
import com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository.ChangePasswordRepository
import com.aneriservices.app.Services.SharedPreferencesService
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.Utility.utilities.SnackbarService
import com.aneriservices.app.ViewModal.ChangePassModal.ChangePassModalFactory
import com.aneriservices.app.ViewModal.ChangePassModal.ChangePassViewModal
import com.aneriservices.app.databinding.FragmentChangePasswordBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChangePasswordFragment : Fragment() {

    private lateinit var sharedPreferencesService: SharedPreferencesService

    lateinit var binding: FragmentChangePasswordBinding
    lateinit var changePassViewModal: ChangePassViewModal
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var changePassword: changePassword
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangePasswordBinding.inflate(layoutInflater)

        sharedPreferencesService = SharedPreferencesService(
            requireActivity(),Utility.keyPrefs
        )
        val apiInterface = APIClient.getApiClientLogin().create(ApiInterface::class.java)
        val repository = ChangePasswordRepository(apiInterface)


        changePassViewModal = ViewModelProvider(
            this,
            ChangePassModalFactory(repository)
        ).get(ChangePassViewModal::class.java)

        coroutineScope.launch {
            binding.ChangePassBtn.setOnClickListener {
                val name = binding.editCurrentpass.text.toString()
                val number = binding.editNewpass.text.toString()
                val email = binding.editConfirmpass.text.toString()
                val id = sharedPreferencesService.getInt(Utility.keyid)
                if (validateLogin(name, number, email)) {
                    changePassword = changePassword(id, name, number, email)
                    changePassword.id?.let { it1 ->
                        changePassViewModal.changePasswordn(
                            it1,
                            changePassword.userName ?: "",
                            changePassword.password,
                            changePassword.newPassword
                        )
                    }

                    Utility.observeOnce(changePassViewModal.ChangePassResult) { result ->
                        result.message?.let { message ->
                            if (message.contains("Timeout occurred", ignoreCase = true)) {
                                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                            } else {
                                if (result.success?.equals(enumResponseResult.success.value) == true) {
                                    binding.layoutWait.progebarlayout.visibility = View.VISIBLE
                                    sharedPreferencesService.deleteAll()

                                    findNavController().navigate(R.id.action_changePasswordFragment_to_login_Fragment)




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

    fun validateLogin(Currentpass: String?, NewPassword: String?, Confirmpass: String?): Boolean {
        if (Currentpass == null || Currentpass.trim().isEmpty()) {
            SnackbarService.snackbar(
                requireView(), Utility.ChangePassError, Snackbar.LENGTH_LONG
            )
            binding.editCurrentpass.error = Utility.ChangePassError
            return false
        }

        if (NewPassword?.isEmpty() == true) {
            SnackbarService.snackbar(
                requireView(), Utility.NotAddPhoneNumber, Snackbar.LENGTH_LONG
            )
            binding.editNewpass.error = Utility.NotAddPhoneNumber

            return false
        }

        if (Confirmpass?.isEmpty() == true) {
            SnackbarService.snackbar(
                requireView(), Utility.AddConfirmPass, Snackbar.LENGTH_LONG
            )
            binding.editConfirmpass.error = Utility.AddConfirmPass
            return false
        }

        if (NewPassword?.length!! <= 4) {
            SnackbarService.snackbar(
                requireView(), Utility.PassAtLeast5Char, Snackbar.LENGTH_LONG
            )
            binding.editNewpass.error = Utility.PassAtLeast5Char
            return false
        }

        if (!NewPassword.equals(Confirmpass)) {
            SnackbarService.snackbar(
                requireView(), Utility.PassAddMustBeSame, Snackbar.LENGTH_LONG
            )
            binding.editConfirmpass.error = Utility.PassAddMustBeSame
            return false
        }
        return true
    }
}