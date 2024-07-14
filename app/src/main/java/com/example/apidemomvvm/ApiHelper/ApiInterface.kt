package com.aneriservices.app.ApiHelper

import com.example.country.CountryModal
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {

    @GET("all")
    fun getcountry(): Call<List<CountryModal>>


//    @POST("account/login")
//    fun getLogInUsers(
//        @Header("accept") accept: String,
//        @Header("userid") userid: String,
//        @Header("Content-Type") contetype: String?,
//        @Body LogInApiDataModal: LogInModal,
//    ): Call<ApiResponseModel<UserMaster>>
//
//
//    @POST("account/modifyUserProfile")
//    fun getUpdateUsers(
//        @Header("userid") userid: String,
//        @Header("Content-Type") contetype: String?,
//        @Body LogInApiDataModal: updateuser,
//    ): Call<ApiResponseModel<UserMaster>>
//
//    @POST("account/changePassword")
//    fun getchangepassword(
//        @Header("accept") accept: String,
//        @Header("userid") userid: String,
//        @Header("Content-Type") contetype: String?,
//        @Body changePassword: changePassword,
//    ): Call<ApiResponseModel<UserMaster>>
//
//
//    @GET("common/getValueDetailList?")
//    fun getSalarySlipReason(
//        @Query("valueName")query: String,
//        @Header("accept") accept: String,
//    ): Call<ApiResponseModelList<comanModal>>
//
//    @POST("request/addsalarySlip")
//    fun getAddSalarySlip(
//        @Header("userid") userid: String,
//        @Header("entryBy") accept: String,
//        @Header("Content-Type") contetype: String?,
//        @Body AddSalarySlipdataModal: AddSalarySlipdataModal,
//    ): Call<ApiResponseModel<UserMaster>>
//
//    @POST("request/getList")
//    fun getSalaryrequestList(
//        @Header("pageIndex") pageIndex: Int,
//        @Header("pageSize") pageSize: Int,
//        @Header("Content-Type") contetype: String?,
//        @Body getGetSallarySlipList: GetGetSallarySlipList,
//    ): Call<ApiResponseModel<EntityReponseModel>>
//
//
//    @POST("workingsheet/getListWorkingSheetForMobileApp")
//    fun workingshet(
//        @Header("pageIndex") pageIndex: Int,
//        @Header("pageSize") pageSize: Int,
//        @Header("Content-Type") contetype: String?,
//        @Body Workingsheet: workingsheet,
//    ): Call<ApiResponseModel<workingsheetReponseModel>>
//
//
//    @GET("feedbackCode/getList")
//    fun feedbackcodelist(
//        @Header("accept") accept: String,
//        @Header("userid") userid: String,
//    ): Call<ApiResponseModelList<FeedbackCodeMasterModel>>
//
//    @Multipart
//    @POST("visit/add")
//    fun feedbackDump(
//        @Header("userid") userid: String,
//        @Header("entryBy") entryBy: String?,
//        @Part("objJsonStr") feedbackRequestJson: FeedbackRequest,
//        @Part file: MultipartBody.Part?,
//    ): Call<ApiResponseModel<visitModel>>
//
//
//    @GET("customercontact/appCustomerAddressGetList?")
//    fun CustomerAddressGetList(
//        @Query("employeeId") employeeId: Int,
//        @Header("pageIndex") pageIndex: Int,
//        @Header("pageSize") pageSize: Int,
//    ): Call<ApiResponseModel<customeraddressModel>>
//
//
//    @GET("customercontact/appCustomerContactGetList?")
//    fun CustomerContactGetList(
//        @Query("employeeId") employeeId: Int,
//        @Header("pageIndex") pageIndex: Int,
//        @Header("pageSize") pageSize: Int,
//    ): Call<ApiResponseModel<customercontactModelList>>

}




