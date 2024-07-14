//package com.aneriservices.app.Repository.FeedbackDump
//
//import android.content.Context
//import android.net.Uri
//import android.provider.OpenableColumns
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.aneriservices.app.ApiHelper.APIClient
//import com.aneriservices.app.ApiHelper.ApiInterface
//import com.aneriservices.app.Modal.Auth.ApiResponseModel
//import com.aneriservices.app.Modal.Auth.FeedbackRequest
//import com.aneriservices.app.Modal.Workingsheetmodal.visitModel
//import com.aneriservices.app.Services.SharedPreferencesService
//import com.aneriservices.app.Utility.Utility
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.File
//import java.io.FileOutputStream
//import java.io.InputStream
//
//class FeedbackAddRepository(private val apiInterface: ApiInterface, private val context: Context) {
//
//    private val _feedbackdump = MutableLiveData<ApiResponseModel<visitModel>>()
//    val FeedBackDump: LiveData<ApiResponseModel<visitModel>> = _feedbackdump
//    private val sharedPreferencesService = SharedPreferencesService(context, Utility.keyPrefs)
//    private val userid = sharedPreferencesService.getString(Utility.keyUserid)
//    var part: MultipartBody.Part? = null
//
//    fun feedbackdumprepo(feedback: FeedbackRequest, uri: Uri?, uri1: Uri?) {
//        if (uri == null && uri1 == null) {
//            Log.e("@@TAG", "Both URIs are null")
//            val call = apiInterface.feedbackDump(APIClient.userid, userid, feedback, null)
//            enqueueCall(call)
//            return
//        }
//        uri?.let { processUri(it, feedback) }
//        uri1?.let { processUri(it, feedback) }
//    }
//
//    private fun enqueueCall(call: Call<ApiResponseModel<visitModel>>) {
//        call.enqueue(object : Callback<ApiResponseModel<visitModel>> {
//            override fun onResponse(
//                call: Call<ApiResponseModel<visitModel>>,
//                response: Response<ApiResponseModel<visitModel>>
//            ) {
//                _feedbackdump.postValue(response.body())
//            }
//
//            override fun onFailure(
//                call: Call<ApiResponseModel<visitModel>>,
//                t: Throwable
//            ) {
//                _feedbackdump.postValue(
//                    ApiResponseModel(
//                        actionType = null,
//                        data = null,
//                        success = null,
//                        message = "Timeout occurred. Please try again.",
//                        errors = null
//                    )
//                )
//            }
//        })
//    }
//
//    private fun processUri(uri: Uri?, feedback: FeedbackRequest) {
//        if (uri == null) {
//            Log.e("#@#TAG", "processUriaaa: "+uri )
//            Log.e("@@TAG", "URI is null")
//            return
//        }
//
//
//        val file = getFileFromUri(uri)
//        Log.e("#@#TAG", "prosssssscessUri: "+file )
//        if (file == null) {
//            Log.e("#@#TAG", "processUridadad: "+file )
//            Log.e("@@TAG", "File is null")
//            return
//        }
//
//        file.let { (actualFile, fileName) ->
//            val mediaType = MediaType.parse(getMimeTypeFromUri(uri))
//            val requestBody = RequestBody.create(mediaType, actualFile)
//            val parts = MultipartBody.Part.createFormData("newfile", fileName, requestBody)
//            part = parts
//        }
//
//        val call = apiInterface.feedbackDump(APIClient.userid, userid, feedback, part)
//
//        call.enqueue(object : Callback<ApiResponseModel<visitModel>> {
//            override fun onResponse(
//                call: Call<ApiResponseModel<visitModel>>,
//                response: Response<ApiResponseModel<visitModel>>
//            ) {
//                _feedbackdump.postValue(response.body())
//            }
//
//            override fun onFailure(
//                call: Call<ApiResponseModel<visitModel>>,
//                t: Throwable
//            ) {
//                _feedbackdump.postValue(
//                    ApiResponseModel(
//                        actionType = null,
//                        data = null,
//                        success = null,
//                        message = "Timeout occurred. Please try again.",
//                        errors = null
//                    )
//                )
//            }
//        })
//    }
//
//    private fun getMimeTypeFromUri(uri: Uri): String? {
//        return context.contentResolver.getType(uri)
//    }
//
//    private fun getFileFromUri(uri: Uri): Pair<File, String>? {
//        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
//        inputStream?.use { input ->
//            val originalFileName = getFileNameFromUri(uri)
//            Log.e("%%TAG", "getFileFromUri: " + originalFileName)
//            originalFileName?.let { fileName ->
//                val file = File(context.cacheDir, fileName)
//                FileOutputStream(file).use { output ->
//                    input.copyTo(output)
//                }
//                return Pair(file, fileName)
//            }
//        }
//        return null
//    }
//
//    private fun getFileNameFromUri(uri: Uri): String? {
//        val cursor = context.contentResolver.query(uri, null, null, null, null)
//        cursor?.use {
//            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//            it.moveToFirst()
//            return it.getString(nameIndex)
//        }
//        return null
//    }
//}
