package com.aneriservices.app.ViewModal.SalarySlipReason

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.EntityReponseModel
import com.aneriservices.app.Repository.SallarySlip.GetSallarySlipListRepository

class SalarySlipListModal(private val getSallarySlipListRepository: GetSallarySlipListRepository) :
    ViewModel() {

    val addSalaryslipResult: LiveData<ApiResponseModel<EntityReponseModel>> =
        getSallarySlipListRepository.GetSallarySlip

    fun getlistSalaryslip(
        fromEmployeeId: Int?,
        pageSize: Int,


        ) {
        getSallarySlipListRepository.GetSallarySlipList(fromEmployeeId, pageSize)

    }



}