package com.aneriservices.app.ViewModal.SalarySlipReason

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.SallarySlip.SalarySlipReasonRepository

class salarySlipReasonModalFactory (private val salarySlipReasonRepository: SalarySlipReasonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SalarySlipReasonModal(salarySlipReasonRepository) as T
    }
}