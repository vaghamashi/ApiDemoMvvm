//package com.aneriservices.app.Repository.Log_In_All_Repository
//
//import android.annotation.SuppressLint
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.LogInModal
//import com.aneriservices.app.Modal.Auth.UserMaster
//import com.aneriservices.app.Utility.Utility
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//class LogInRepository(private val apiInterface: ApiInterface) {
//
//    private val _login = MutableLiveData<ApiResponseModel<UserMaster>>()
//    val login: LiveData<ApiResponseModel<UserMaster>> = _login
//
//
//     @SuppressLint("SuspiciousIndentation")
//     fun login(username: String, password: String, deviceId: String, source: Int) {
//        val loginModal = LogInModal(username, password, deviceId, source)
//
////        coroutineScope.launch {
//            try {
//
//                val call = apiInterface.getLogInUsers(
//                    APIClient.accept,
//                    APIClient.userid,
//                    APIClient.ContentType,
//                    loginModal
//                )
//                call.enqueue(object : Callback<ApiResponseModel<UserMaster>> {
//                    override fun onResponse(
//                        call: Call<ApiResponseModel<UserMaster>>,
//                        response: Response<ApiResponseModel<UserMaster>>
//                    ) {
//                        Log.e("tag", "response : " + Utility.getJsonStrFromObject(response.body()))
//                        _login.postValue(response.body())
//
//                    }
//
//                    override fun onFailure(call: Call<ApiResponseModel<UserMaster>>, t: Throwable) {
//
//                        _login.postValue( ApiResponseModel(
//                            actionType = null,
//                            data = null,
//                            success = null,
//                            message = "Timeout occurred. Please try again.",
//                            errors = null,
//                        ))
//                    }
//                })
//
//
//
//            } catch (e: Exception) {
//
//            }
//
//    }
//}
//
//
