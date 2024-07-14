package com.aneriservices.app.Fragment.Profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.R
import com.aneriservices.app.databinding.FragmentPermissionVerificationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PermissionVerificationFragment : Fragment() {
    private val CONTACTS_PERMISSION_REQUEST_CODE = 101
    private val Location_PERMISSION_REQUEST_CODE = 102
    private val NOTIFICATIONS_PERMISSION_REQUEST_CODE = 103
    private val CALL_LOG_PERMISSION_REQUEST_CODE = 104
    private val STORAGE_PERMISSION_REQUEST_CODE = 105
    lateinit var binding: FragmentPermissionVerificationBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPermissionVerificationBinding.inflate(layoutInflater)

        setupPermissions()


        coroutineScope.launch {
            binding.imageContacts.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked && isPermissionGranted(Manifest.permission.READ_CONTACTS)) {
                    binding.imageContacts.isChecked = true
                } else {
                    if (isPermissionDenied(Manifest.permission.READ_CONTACTS)) {
                        navigateToAppSettings()
                    } else {
                        requestContactsPermission()
                    }
                }
            }

            binding.btnStorege.setOnClickListener {
                if (isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {

                    if (isPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        navigateToAppSettings()
                    } else {
                        requestLocationPermission()
                    }
                    requestStoragePermission()
                }
            }

            binding.iamageLocation.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked && isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    binding.iamageLocation.isChecked = true
                } else {
                    if (isPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        navigateToAppSettings()
                    } else {
                        requestLocationPermission()
                    }

                }
            }
            binding.imageNotification.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked && isPermissionGranted(Manifest.permission.POST_NOTIFICATIONS)) {
                    binding.imageNotification.isChecked = true
                } else {
                    if (isPermissionDenied(Manifest.permission.POST_NOTIFICATIONS)) {
                        navigateToAppSettings()
                    } else {
                        requestNotificationPermission()
                    }

                }
            }
            binding.imageCalllogo.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked && isPermissionGranted(Manifest.permission.READ_CALL_LOG)) {
                    binding.imageCalllogo.isChecked = true

                } else {
                    if (isPermissionDenied(Manifest.permission.READ_CALL_LOG)) {
                        navigateToAppSettings()
                    } else {
                        requestCallLogPermission()
                    }
                }
            }
            binding.icBack.setOnClickListener {

                findNavController().popBackStack()

            }
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController().popBackStack()
                    }
                })
        }

        return binding.root
    }
    private fun isPermissionDenied(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)
    }
    override fun onResume() {
        setupPermissions()
        super.onResume()
    }
    private fun requestCallLogPermission() {
        Log.e("@@TAG", "setupPermissions: "+"calllogs" )
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_CALL_LOG),
            CALL_LOG_PERMISSION_REQUEST_CODE
        )
    }
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            NOTIFICATIONS_PERMISSION_REQUEST_CODE
        )
    }
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            Location_PERMISSION_REQUEST_CODE
        )
    }
    private fun setupPermissions() {
        if (isPermissionGranted(Manifest.permission.READ_CONTACTS)) {
            binding.imageContacts.isChecked = true

        } else {
            binding.imageContacts.isChecked = false
        }
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            binding.iamageLocation.isChecked = true
        } else {
            binding.iamageLocation.isChecked = false
        }
        if (isPermissionGranted(Manifest.permission.POST_NOTIFICATIONS)) {
            binding.imageNotification.isChecked = true
        } else {

            binding.imageNotification.isChecked = false
        }

        if (isPermissionGranted(Manifest.permission.READ_CALL_LOG)) {
            Log.e("@@TAG", "setupPermissions: "+"true" )
            binding.imageCalllogo.isChecked = true
        } else {
            binding.imageCalllogo.isChecked = false
            Log.e("@@TAG", "setupPermissions: "+"flase" )
        }

        if (isPermissionGranted(Manifest.permission.MANAGE_EXTERNAL_STORAGE)){
            binding.imageStorage.isChecked = true
        }else{
            binding.imageStorage.isChecked = false
        }

    }
    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestContactsPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_CONTACTS),
            CONTACTS_PERMISSION_REQUEST_CODE
        )
    }
    private fun navigateToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}