package com.aneriservices.app.ViewModal.ChangePassModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Auth.UserMaster
import com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository.ChangePasswordRepository

class ChangePassViewModal(private val changePasswordRepository: ChangePasswordRepository) : ViewModel() {

    val ChangePassResult: LiveData<ApiResponseModel<UserMaster>> = changePasswordRepository.ChangePass
    fun changePasswordn(
        id: Int,
        userName: String,
        password: String,
        newPassword: String
    ) {
        changePasswordRepository.ChangePass(id, password,userName, newPassword)

    }}