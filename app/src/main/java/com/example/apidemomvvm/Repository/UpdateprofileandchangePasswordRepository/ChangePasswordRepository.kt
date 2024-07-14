//package com.aneriservices.app.Repository.UpdateprofileandchangePasswordRepository
//
//import android.annotation.SuppressLint
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.UserMaster
//import com.aneriservices.app.Modal.Auth.changePassword
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ChangePasswordRepository(private val apiInterface: ApiInterface) {
//
//    private val _ChangePass = MutableLiveData<ApiResponseModel<UserMaster>>()
//    val ChangePass: LiveData<ApiResponseModel<UserMaster>> = _ChangePass
//
//    @SuppressLint("SuspiciousIndentation")
//    fun ChangePass(id: Int, username: String, password: String, newPassword: String) {
//        val changePassword = changePassword(id, username, password, newPassword)
//
////        coroutineScope.launch {
//        try {
//            /*val response = withContext(Dispatchers.IO) {
//                apiInterface.getLogInUsers(
//                    APIClient.accept, APIClient.userid, APIClient.ContentType, loginModal
//                ).clone().execute()
//            }*/
//            val call = apiInterface.getchangepassword(
//                APIClient.accept,
//                APIClient.userid,
//                APIClient.ContentType,
//                changePassword
//            )
//            call.enqueue(object : Callback<ApiResponseModel<UserMaster>> {
//                override fun onResponse(
//                    call: Call<ApiResponseModel<UserMaster>>,
//                    response: Response<ApiResponseModel<UserMaster>>
//                ) {
//                    Log.e("tag", "response : " + Utility.getJsonStrFromObject(response.body()))
//                    _ChangePass.postValue(response.body())
//
//                }
//
//                override fun onFailure(call: Call<ApiResponseModel<UserMaster>>, t: Throwable) {
//
//                    _ChangePass.postValue( ApiResponseModel(
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
//
//        }
////        }
//    }
//}