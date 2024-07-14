package com.aneriservices.app.ViewModal.LogInViewModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.Log_In_All_Repository.LogInRepository

class LogInModalFactory (private val logInRepository: LogInRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LogInViewModal(logInRepository) as T
    }
}