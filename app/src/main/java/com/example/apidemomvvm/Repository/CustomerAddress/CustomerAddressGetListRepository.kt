//package com.aneriservices.app.Repository.CustomerAddress
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.DatabaseHelper.CustomerAddress.CustomerAddressEntitty
//import com.aneriservices.app.DatabaseHelper.WorkingSheet.appDatabase
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Workingsheetmodal.customeraddressModel
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class CustomerAddressGetListRepository(
//    val apiInterface: ApiInterface,
//    private val appDatabase: appDatabase
//) {
//
//    private val _CustomerAddressGetList = MutableLiveData<ApiResponseModel<customeraddressModel>>()
//    val customerAddressGetrepo: LiveData<ApiResponseModel<customeraddressModel>> =
//        _CustomerAddressGetList
//
//    fun CustomerAddressGetList(fromEmployeeId: Int, pageIndex: Int) {
//        var call = apiInterface.CustomerAddressGetList(fromEmployeeId, pageIndex, Utility.pageSize)
//        call.enqueue(object : Callback<ApiResponseModel<customeraddressModel>> {
//            override fun onResponse(
//                call: Call<ApiResponseModel<customeraddressModel>>,
//                response: Response<ApiResponseModel<customeraddressModel>>
//            ) {
//                Log.e("##TAG", "onResponse: " + response.body())
//                _CustomerAddressGetList.postValue(response.body())
//                response.body()?.data?.data?.forEach {
//                    val data = CustomerAddressEntitty(
//                        it.id,
//                        it.portfolioTypeId,
//                        it.clientId,
//                        it.trackingNo,
//                        it.addressTypeId,
//                        it.address,
//                        it.landmark,
//                        it.mainArea,
//                        it.area,
//                        it.subArea,
//                        it.sBarea,
//                        it.cityName,
//                        it.pincode,
//                        it.parentType,
//                        it.latitude,
//                        it.longitude,
//                        it.isLivesHere
//                    )
//                    appDatabase.CustomerAddressMaster().addCustomerAddress(data)
//
//                }
//            }
//
//            override fun onFailure(
//                call: Call<ApiResponseModel<customeraddressModel>>, t: Throwable
//            ) {
//                _CustomerAddressGetList.postValue(
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