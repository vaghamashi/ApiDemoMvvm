package com.aneriservices.app.ViewModal.SalarySlipReason

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.SallarySlip.AddSalarySlipRepository
import com.aneriservices.app.Repository.SallarySlip.GetSallarySlipListRepository

class GetSalarySlipFactoryModal(private val getSallarySlipListRepository: GetSallarySlipListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SalarySlipListModal(getSallarySlipListRepository) as T
    }

}