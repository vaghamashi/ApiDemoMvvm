package com.aneriservices.app.ViewModal.SalarySlipReason

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.SallarySlip.AddSalarySlipRepository

class AddSalarySlipModaFactory (private val addSalarySlipRepository: AddSalarySlipRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddSalarySlipModal(addSalarySlipRepository) as T
    }
}