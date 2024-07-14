package com.aneriservices.app.ViewModal.FeedBackDumpModal

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aneriservices.app.Modal.Auth.ApiResponseModel
import com.aneriservices.app.Modal.Auth.FeedbackRequest
import com.aneriservices.app.Modal.Workingsheetmodal.visitModel
import com.aneriservices.app.Repository.FeedbackDump.FeedbackAddRepository

class FeedbackDumpModal(private val feedbackAddRepository: FeedbackAddRepository) : ViewModel() {


    val workingsshetresult: LiveData<ApiResponseModel<visitModel>> =
        feedbackAddRepository.FeedBackDump

    fun getworkingshetdata(
        feedback: FeedbackRequest, files: Uri?,files1: Uri?
    ) {
        feedbackAddRepository.feedbackdumprepo(feedback, files,files1)
    }


}