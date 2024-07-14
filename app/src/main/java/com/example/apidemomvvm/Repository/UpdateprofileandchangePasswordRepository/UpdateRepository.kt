//package com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.UserMaster
//import com.aneriservices.app.Modal.Auth.updateuser
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class UpdateRepository(private val apiInterface: ApiInterface) {
//    private val _Update = MutableLiveData<ApiResponseModel<UserMaster>>()
//    val Update: LiveData<ApiResponseModel<UserMaster>> = _Update
//
//
//
//    fun Update(id: Int, displayName: String, mobileNo: String, email: String) {
//        val updateuser = updateuser(id, displayName, mobileNo, email)
//        try {
//            val call = apiInterface.getUpdateUsers(
//                 APIClient.userid, APIClient.ContentType, updateuser
//            )
//            call.enqueue(object : Callback<ApiResponseModel<UserMaster>> {
//                override fun onResponse(
//                    call: Call<ApiResponseModel<UserMaster>>,
//                    response: Response<ApiResponseModel<UserMaster>>
//                ) {
//                    Log.e("tag", "response : " + Utility.getJsonStrFromObject(response.body()))
//                    _Update.postValue(response.body())
//                }
//                override fun onFailure(call: Call<ApiResponseModel<UserMaster>>, t: Throwable) {
//
//                    _Update.postValue(
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
//
//    }
//
//
//}