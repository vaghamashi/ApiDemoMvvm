//package com.aneriservices.app.Repository.SallarySlip
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.Modal.Auth.AddSalarySlipdataModal
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.UserMaster
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class AddSalarySlipRepository(private val apiInterface: ApiInterface) {
//
//    private val _AddSalarySlip = MutableLiveData<ApiResponseModel<UserMaster>>()
//    val AddSalarySlip: LiveData<ApiResponseModel<UserMaster>> = _AddSalarySlip
//
//    fun addsalaryslip(employeeId: Int, months: String, purpose: String) {
//        val addsalarySlip = AddSalarySlipdataModal(employeeId, months, purpose)
//
//        try {
//
//            val call = apiInterface.getAddSalarySlip(
//                APIClient.userid,
//                APIClient.entryBy,
//                APIClient.ContentType,
////                addsalarySlip
//            )
//            call.enqueue(object : Callback<ApiResponseModel<UserMaster>> {
//                override fun onResponse(
//                    call: Call<ApiResponseModel<UserMaster>>,
//                    response: Response<ApiResponseModel<UserMaster>>
//                ) {
//
//                    _AddSalarySlip.postValue(response.body())
//
//                }
//
//                override fun onFailure(call: Call<ApiResponseModel<UserMaster>>, t: Throwable) {
//                    _AddSalarySlip.postValue( ApiResponseModel(
//                        actionType = null,
//                        data = null,
//                        success = null,
//                        message = "Timeout occurred. Please try again.",
//                        errors = null,
//                    ))
//                }
//            })
//
//
//
//        } catch (e: Exception) {
//        }
//
//    }
//}