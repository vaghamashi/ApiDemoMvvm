package com.aneriservices.app.ViewModal.UpdateViewModal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Auth.UserMaster
import com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository.UpdateRepository

class UdapteViewModal(private val updateRepository: UpdateRepository) : ViewModel() {
    val UdapteResult: LiveData<ApiResponseModel<UserMaster>> = updateRepository.Update
    fun update(
        id: Int,
        displayName: String,
        mobileNo: String,
        email: String
    ) {
        updateRepository.Update(id , displayName,mobileNo, email)

    }
}