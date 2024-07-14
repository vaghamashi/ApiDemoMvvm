package com.aneriservices.app.ViewModal.CustomerAddressGetListModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.CustomerAddress.CustomerAddressGetListRepository

class CustomerAddressFactoryModal(private val customerAddressGetListRepository: CustomerAddressGetListRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerAddressListModal(customerAddressGetListRepository) as T
    }
}

