package com.aneriservices.app.ViewModal.FeedbackCodeModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Auth.ApiResponseModelList
import com.aneriservices.app.Modal.FeedbackCodeMasterModel
import com.aneriservices.app.Repository.FeedBackRepository.FeedBackCodeListRepository

class FeedBackCodeListModal(private val feedBackCodeListRepository: FeedBackCodeListRepository) :
    ViewModel() {
    init {
        getlistFeedBackCodeList()
    }
    val addFeedbackCodeListResult: LiveData<ApiResponseModelList<FeedbackCodeMasterModel>> =
        feedBackCodeListRepository.feedbacklistget


    fun getlistFeedBackCodeList() {
        feedBackCodeListRepository.feedbackcodelist()

    }
}