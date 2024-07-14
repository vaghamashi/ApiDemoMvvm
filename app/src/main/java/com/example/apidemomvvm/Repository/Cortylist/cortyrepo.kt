package com.example.apidemomvvm.Repository.Cortylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aneriservices.app.ApiHelper.ApiInterface
import com.example.country.Translations

class cortyrepo (private val apiInterface: ApiInterface){

    private val _GetSallarySlipList = MutableLiveData<Translations>()
    val GetSallarySlip: LiveData<Translations> = _GetSallarySlipList
//
//
    fun GetSallarySlipList(fromEmployeeId: Int?, pageIndex: Int?) {
        val SallarySlipList = getcountry(fromEmployeeId)

        Log.e("tag","GetSallarySlipList")

        try {

            val call = pageIndex?.let {
                apiInterface.getSalaryrequestList(
                    it, Utility.pageSize, APIClient.ContentType, SallarySlipList
                )
            }
            call?.enqueue(object : Callback<ApiResponseModel<EntityReponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponseModel<EntityReponseModel>>,
                    response: Response<ApiResponseModel<EntityReponseModel>>
                ) {
                    _GetSallarySlipList.postValue(response.body())

                }

                override fun onFailure(
                    call: Call<ApiResponseModel<EntityReponseModel>>, t: Throwable
                ) {
                    _GetSallarySlipList.postValue(
                        ApiResponseModel(
                            actionType = null,
                            data = null,
                            success = null,
                            message = "Timeout occurred. Please try again.",
                            errors = null,
                        )
                    )
                }
            })


        } catch (e: Exception) {

        }

    }

}
}