package com.aneriservices.app.ViewModal.UpdateViewModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository.UpdateRepository

class UpdateModalFactory (private val updateRepository: UpdateRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UdapteViewModal(updateRepository) as T
    }
}