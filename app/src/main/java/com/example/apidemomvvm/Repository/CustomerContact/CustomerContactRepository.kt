//package com.aneriservices.app.Repository.CustomerContact
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.DatabaseHelper.CustomerContact.CustomerContactEntity
//import com.aneriservices.app.DatabaseHelper.WorkingSheet.appDatabase
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Workingsheetmodal.customercontactModelList
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class CustomerContactRepository(
//    val apiInterface: ApiInterface,
//    private val appDatabase: appDatabase
//) {
//    private val _CustomerContactRepository =
//        MutableLiveData<ApiResponseModel<customercontactModelList>>()
//    val customerContactGetrepo: LiveData<ApiResponseModel<customercontactModelList>> =
//        _CustomerContactRepository
//
//
//    fun CustomerContactGetList(fromEmployeeId: Int, pageIndex: Int) {
//        var call = apiInterface.CustomerContactGetList(fromEmployeeId, pageIndex, Utility.pageSize)
//        call.enqueue(object : Callback<ApiResponseModel<customercontactModelList>> {
//            override fun onResponse(
//                call: Call<ApiResponseModel<customercontactModelList>>,
//                response: Response<ApiResponseModel<customercontactModelList>>
//            ) {
//                Log.e("tag", "response : " + Utility.getJsonStrFromObject(response.body()))
//                _CustomerContactRepository.postValue(response.body())
//
//                response.body()?.data?.data?.forEach {
//                    val data = CustomerContactEntity(
//                        it.id,
//                        it.portfolioType,
//                        it.clientId,
//                        it.trackingNo,
//                        it.contactType?.value,
//                        it.name,
//                        it.contactNumber,
//                        it.relation,
//                        it.parentType,
//                        it.callSource?.value,
//                        it.lastCallDateTime,
//                        it.totalConnectedCount,
//                        it.totalNotConnectedCount,
//                        it.remarks
//                    )
//                    appDatabase.CustomerContactMaster().addCustomerContact(data)
//                }
//
//            }
//
//            override fun onFailure(
//                call: Call<ApiResponseModel<customercontactModelList>>, t: Throwable
//            ) {
//                _CustomerContactRepository.postValue(
//                    ApiResponseModel(
//                        actionType = null,
//                        data = null,
//                        success = null,
//                        message = "Timeout occurred. Please try again.",
//                        errors = null,
//                    )
//                )
//
//            }
//
//
//        })
//
//    }
//
//}