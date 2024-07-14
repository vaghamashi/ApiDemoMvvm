//package com.aneriservices.app.Repository.SallarySlip
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.GetGetSallarySlipList
//import com.aneriservices.app.Modal.EntityReponseModel
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class GetSallarySlipListRepository(private val apiInterface: ApiInterface) {
//
//    private val _GetSallarySlipList = MutableLiveData<ApiResponseModel<EntityReponseModel>>()
//    val GetSallarySlip: LiveData<ApiResponseModel<EntityReponseModel>> = _GetSallarySlipList
//
//
//    fun GetSallarySlipList(fromEmployeeId: Int?, pageIndex: Int?) {
//        val SallarySlipList = GetGetSallarySlipList(fromEmployeeId)
//
//        Log.e("tag","GetSallarySlipList")
//
//        try {
//
//            val call = pageIndex?.let {
//                apiInterface.getSalaryrequestList(
//                    it, Utility.pageSize, APIClient.ContentType, SallarySlipList
//                )
//            }
//            call?.enqueue(object : Callback<ApiResponseModel<EntityReponseModel>> {
//                override fun onResponse(
//                    call: Call<ApiResponseModel<EntityReponseModel>>,
//                    response: Response<ApiResponseModel<EntityReponseModel>>
//                ) {
//                    _GetSallarySlipList.postValue(response.body())
//
//                }
//
//                override fun onFailure(
//                    call: Call<ApiResponseModel<EntityReponseModel>>, t: Throwable
//                ) {
//                    _GetSallarySlipList.postValue(
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
//
//
//        } catch (e: Exception) {
//
//        }
//
//    }
//
//}