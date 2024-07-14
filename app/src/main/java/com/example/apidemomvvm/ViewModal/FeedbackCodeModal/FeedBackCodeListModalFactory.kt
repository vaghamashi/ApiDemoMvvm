package com.aneriservices.app.ViewModal.FeedbackCodeModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aneriservices.app.Repository.FeedBackRepository.FeedBackCodeListRepository

class FeedBackCodeListModalFactory(private val feedBackCodeListRepository: FeedBackCodeListRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedBackCodeListModal(feedBackCodeListRepository) as T
    }
}