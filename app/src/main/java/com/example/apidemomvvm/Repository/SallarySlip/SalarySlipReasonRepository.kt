//package com.aneriservices.app.Repository.SallarySlip
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.Modal.Auth.ApiResponseModelList
//import com.aneriservices.app.Modal.comanModal
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class SalarySlipReasonRepository(private val apiInterface: ApiInterface) {
//    private val _SalarySlipReason = MutableLiveData<ApiResponseModelList<comanModal>>()
//    val SalarySlipReason: LiveData<ApiResponseModelList<comanModal>> = _SalarySlipReason
//    fun SlipReason() {
//        try {
//            val call = apiInterface.getSalarySlipReason("SalarySlipReason", APIClient.accept)
//            call.enqueue(object : Callback<ApiResponseModelList<comanModal>> {
//                override fun onResponse(
//                    call: Call<ApiResponseModelList<comanModal>>,
//                    response: Response<ApiResponseModelList<comanModal>>
//                ) {
//                    _SalarySlipReason.postValue(response.body())
//                }
//                override fun onFailure(
//                    call: Call<ApiResponseModelList<comanModal>>, t: Throwable
//                ) {
//                    _SalarySlipReason.postValue(
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
//
//        }
//
//
//    }
//}