//package com.aneriservices.app.Repository.FeedBackRepository
//
//import android.annotation.SuppressLint
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.DatabaseHelper.FeedBackCodeList.FeedbackCodeEntitty
//import com.aneriservices.app.DatabaseHelper.WorkingSheet.appDatabase
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.ApiResponseModelList
//import com.aneriservices.app.Modal.FeedbackCodeMasterModel
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class FeedBackCodeListRepository(
//    private val apiInterface: ApiInterface, private val appDatabase: appDatabase
//) {
//    private val _feedbackcodelistget = MutableLiveData<ApiResponseModelList<FeedbackCodeMasterModel>>()
//    val feedbacklistget: LiveData<ApiResponseModelList<FeedbackCodeMasterModel>> = _feedbackcodelistget
//
//    fun feedbackcodelist() {
//        try {
//            val call = apiInterface.feedbackcodelist(
//                APIClient.accept, APIClient.userid
//            )
//
//            call?.enqueue(object : Callback<ApiResponseModelList<FeedbackCodeMasterModel>> {
//                @SuppressLint("SuspiciousIndentation")
//                override fun onResponse(
//                    call: Call<ApiResponseModelList<FeedbackCodeMasterModel>>,
//                    response: Response<ApiResponseModelList<FeedbackCodeMasterModel>>
//                ) {
//                    _feedbackcodelistget.postValue(response.body())
//
//                    Log.e("@@TAG", "onResponseeee : " + Utility.getJsonStrFromObject(response.body()))
//                    Log.e("@@TAG", "onResponseeeeeeee: "+response.body() )
//
//                    response.body()?.data?.forEach { feedbackCodeMasterModel ->
//                        val feedbackCode = FeedbackCodeEntitty(
//                            id = feedbackCodeMasterModel.id,
//                            code = feedbackCodeMasterModel.code,
//                            name = feedbackCodeMasterModel.name,
//                            requiredFollowupDate = feedbackCodeMasterModel.requiredFollowupDate,
//                            isFollowupDateMandatory = feedbackCodeMasterModel.isFollowupDateMandatory,
//                            isFollowupTimeMandatory = feedbackCodeMasterModel.isFollowupTimeMandatory,
//                            isPositive = feedbackCodeMasterModel.isPositive,
//                            isCallingCode = feedbackCodeMasterModel.isCallingCode,
//                            isVisitCode = feedbackCodeMasterModel.isVisitCode,
//                            isReviewCode = feedbackCodeMasterModel.isReviewCode,
//                            isActive = feedbackCodeMasterModel.isActive
//                        )
//
//                        appDatabase.feedbackCodeMaster().addFeedbackCode(feedbackCode)
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<ApiResponseModelList<FeedbackCodeMasterModel>>, t: Throwable
//                ) {
//                    _feedbackcodelistget.postValue(
//                        ApiResponseModelList(
//                            actionType = null,
//                            data = null,
//                            success = null,
//                            message = "Timeout occurred. Please try again.",
//                            errors = null,
//                        )
//                    )
//                }
//            })
//        } catch (e: Exception) {
//        }
//    }
//}