package com.aneriservices.app.ViewModal.LogInViewModal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Auth.UserMaster
import com.aneriservices.app.Repository.Log_In_All_Repository.LogInRepository

class LogInViewModal(private val logInRepository: LogInRepository) : ViewModel() {

    val loginResult: LiveData<ApiResponseModel<UserMaster>> = logInRepository.login
     fun login(
        username: String,
        password: String,
        deviceId: String,
        loginSource: Int
    ) {
        logInRepository.login(username, password,deviceId, loginSource)

    }
}