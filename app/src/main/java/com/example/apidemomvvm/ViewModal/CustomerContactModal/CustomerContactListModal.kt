package com.aneriservices.app.ViewModal.CustomerContactModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Workingsheetmodal.customercontactModelList
import com.aneriservices.app.Repository.CustomerContact.CustomerContactRepository

class CustomerContactListModal (private val customerContactRepository: CustomerContactRepository) :
    ViewModel() {

    val CustomerContactresult: LiveData<ApiResponseModel<customercontactModelList>> =
        customerContactRepository.customerContactGetrepo

    fun CustomerContactdata(
        fromEmployeeId: Int,
        pageSize: Int,
    ) {
        customerContactRepository.CustomerContactGetList(fromEmployeeId, pageSize)
    }


}