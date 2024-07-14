package com.aneriservices.app.ViewModal.CustomerAddressGetListModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Workingsheetmodal.customeraddressModel
import com.aneriservices.app.Modal.workingsheetReponseModel
import com.aneriservices.app.Repository.CustomerAddress.CustomerAddressGetListRepository

class CustomerAddressListModal(private val customerAddressGetListRepository: CustomerAddressGetListRepository) :
    ViewModel() {

    val CustomerAddressresult: LiveData<ApiResponseModel<customeraddressModel>> =
        customerAddressGetListRepository.customerAddressGetrepo

    fun CustomerAddressdata(
        fromEmployeeId: Int,
        pageSize: Int,
    ) {
        customerAddressGetListRepository.CustomerAddressGetList(fromEmployeeId, pageSize)
    }


}