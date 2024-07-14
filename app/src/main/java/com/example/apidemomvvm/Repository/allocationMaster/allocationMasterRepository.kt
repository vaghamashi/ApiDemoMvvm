//package com.aneriservices.app.Repository.allocationMaster
//
//import android.annotation.SuppressLint
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.DatabaseHelper.WorkingSheet.allocationMasterEntitty
//import com.aneriservices.app.DatabaseHelper.WorkingSheet.appDatabase
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.workingsheet
//import com.aneriservices.app.Modal.workingsheetReponseModel
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class allocationMasterRepository(
//    private val apiInterface: ApiInterface, private val appDatabase: appDatabase
//) {
//
//    private val _workingsheetget = MutableLiveData<ApiResponseModel<workingsheetReponseModel>>()
//    val workingsheetrepo: LiveData<ApiResponseModel<workingsheetReponseModel>> = _workingsheetget
//
//    fun workingsheetList(fromEmployeeId: Int?, pageIndex: Int?) {
//        val workingsheetlist = workingsheet(fromEmployeeId, null)
//
//
//        try {
//            val call = pageIndex?.let {
//                apiInterface.workingshet(
//                    it, Utility.pageSize, APIClient.ContentType, workingsheetlist
//                )
//            }
//            call?.enqueue(object : Callback<ApiResponseModel<workingsheetReponseModel>> {
//                @SuppressLint("SuspiciousIndentation")
//                override fun onResponse(
//                    call: Call<ApiResponseModel<workingsheetReponseModel>>,
//                    response: Response<ApiResponseModel<workingsheetReponseModel>>
//                ) {
//                    _workingsheetget.postValue(response.body())
//                    response.body()?.data?.data?.forEach {
//                        val data = allocationMasterEntitty(
//                            it.allocationId,
//                            it.trackingNo,
//                            it.cardNumber,
//                            it.customerName,
//                            it.productId,
//                            it.productName,
//                            it.clientId,
//                            it.clientName,
//                            it.stateId,
//                            it.stateName,
//                            it.branchId,
//                            it.branchName,
//                            it.lastMonthBucket,
//                            it.bucket,
//                            it.cycle,
//                            it.dpd,
//                            it.dpdCate,
//                            it.cityId,
//                            it.cityName,
//                            it.stabAmount,
//                            it.rollbackAmount1,
//                            it.totalOutstanding,
//                            it.principalBalance,
//                            it.totalOverdue,
//                            it.callingFeedbackId,
//                            it.telecallerId,
//                            it.teleCallerName,
//                            it.callingFeedbackCode,
//                            it.callingFollowupDate.let { Utility.getDBFrometDate(it) },
//                            it.callingAttempts,
//                            it.fceFeedbackId,
//                            it.fceId,
//                            it.fceName,
//                            it.fceFeedbackCode,
//                            it.fceFollowupDate.let { Utility.getDBFrometDate(it) },
//                            it.fceAttempts,
//                            it.teamLeaderFeedbackId,
//                            it.teamLeaderId,
//                            it.teamLeaderName,
//                            it.teamLeaderFeedbackCode,
//                            it.teamLeaderFollowupDate.let { Utility.getDBFrometDate(it) },
//                            it.teamLeaderAttempts,
//                            it.dueDetails,
//                            it.portofolioTypeId,
//                            it.collectionManagerId,
//                            it.collectionManagerName,
//                            it.allocationStartDate.let { Utility.getDBFrometDate(it) },
//                            it.allocationEndDate.let { Utility.getDBFrometDate(it) })
//
//                        appDatabase.allocationMaster().adddata(data)
//
//
//
//                    }
//
//                }
//
//                override fun onFailure(
//                    call: Call<ApiResponseModel<workingsheetReponseModel>>, t: Throwable
//                ) {
//                    _workingsheetget.postValue(
//                        ApiResponseModel(
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
//
//}