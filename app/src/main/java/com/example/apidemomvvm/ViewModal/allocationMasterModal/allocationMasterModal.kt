package com.aneriservices.app.ViewModal.allocationMasterModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.workingsheetReponseModel
import com.aneriservices.app.Repository.allocationMaster.allocationMasterRepository

class allocationMasterModal(private val allocationMasterRepository: allocationMasterRepository) :
    ViewModel() {


    val workingsshetresult: LiveData<ApiResponseModel<workingsheetReponseModel>> =
        allocationMasterRepository.workingsheetrepo

    fun getworkingshetdata(
        fromEmployeeId: Int?,
        pageSize: Int,
    ) {
        allocationMasterRepository.workingsheetList(fromEmployeeId, pageSize)
    }
}