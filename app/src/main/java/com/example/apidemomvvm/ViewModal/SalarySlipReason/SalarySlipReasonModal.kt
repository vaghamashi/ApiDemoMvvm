package com.aneriservices.app.ViewModal.SalarySlipReason

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModelList
import com.aneriservices.app.Modal.comanModal
import com.aneriservices.app.Repository.SallarySlip.SalarySlipReasonRepository

class SalarySlipReasonModal(private val salarySlipReasonRepository: SalarySlipReasonRepository) : ViewModel() {


    init {
        SalarySlip()
    }
    val salarySlipReasonResult: LiveData<ApiResponseModelList<comanModal>> =
        salarySlipReasonRepository.SalarySlipReason

    fun SalarySlip() {
        salarySlipReasonRepository.SlipReason()

    }

}