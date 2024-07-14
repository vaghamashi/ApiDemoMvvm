package com.aneriservices.app.ViewModal.SalarySlipReason

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Auth.UserMaster
import com.aneriservices.app.Repository.SallarySlip.AddSalarySlipRepository

class AddSalarySlipModal(private val addSalarySlipRepository: AddSalarySlipRepository) :
    ViewModel() {

    val addSalaryslipResult: LiveData<ApiResponseModel<UserMaster>> = addSalarySlipRepository.AddSalarySlip
    fun addSalaryslip(
        username: Int,
        password: String,
        deviceId: String,

        ) {
        addSalarySlipRepository.addsalaryslip(username, password, deviceId)


    }
}