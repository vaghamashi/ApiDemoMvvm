package com.aneriservices.app.ViewModal.FeedBackDumpModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.FeedbackDump.FeedbackAddRepository

class FeedbackDumpFactoryModal(private val feedbackAddRepository: FeedbackAddRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedbackDumpModal(feedbackAddRepository) as T
    }

}