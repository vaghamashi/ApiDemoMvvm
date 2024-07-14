package com.aneriservices.app.ViewModal.CustomerContactModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.CustomerContact.CustomerContactRepository
import com.aneriservices.app.ViewModal.CustomerAddressGetListModal.CustomerAddressListModal

class CustomerContactFactoryModal(private val customerContactRepository: CustomerContactRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomerContactListModal(customerContactRepository) as T
    }
}