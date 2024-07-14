package com.aneriservices.app.ViewModal.ChangePassModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.Log_In_All_Repository.LogInRepository
import com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository.ChangePasswordRepository

class ChangePassModalFactory (private val changePasswordRepository: ChangePasswordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChangePassViewModal(changePasswordRepository) as T
    }
}