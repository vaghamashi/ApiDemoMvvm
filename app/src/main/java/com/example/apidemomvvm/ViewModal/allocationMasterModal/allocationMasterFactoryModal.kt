package com.aneriservices.app.ViewModal.allocationMasterModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.allocationMaster.allocationMasterRepository

class allocationMasterFactoryModal(private val allocationMasterRepository: allocationMasterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return allocationMasterModal(allocationMasterRepository) as T
    }

}