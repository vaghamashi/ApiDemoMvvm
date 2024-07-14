package com.aneriservices.app.Fragment.Profile

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aneriservices.app.ApiHelper.APIClient
import com.aneriservices.app.ApiHelper.ApiInterface
import com.aneriservices.app.DatabaseHelper.WorkingSheet.appDatabase
import com.aneriservices.app.R
import com.aneriservices.app.Repository.CustomerAddress.CustomerAddressGetListRepository
import com.aneriservices.app.Repository.CustomerContact.CustomerContactRepository
import com.aneriservices.app.Repository.FeedBackRepository.FeedBackCodeListRepository
import com.aneriservices.app.Repository.allocationMaster.allocationMasterRepository
import com.aneriservices.app.Services.BackgroundServices.allocationmasterBackground
import com.aneriservices.app.Services.BackgroundServices.allocationmasterBackground.Companion.adddata
import com.aneriservices.app.Services.BackgroundServices.allocationmasterBackground.Companion.removeservice
import com.aneriservices.app.Services.SharedPreferencesService
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.Utility.utilities.SnackbarService
import com.aneriservices.app.ViewModal.CustomerAddressGetListModal.CustomerAddressFactoryModal
import com.aneriservices.app.ViewModal.CustomerAddressGetListModal.CustomerAddressListModal
import com.aneriservices.app.ViewModal.CustomerContactModal.CustomerContactFactoryModal
import com.aneriservices.app.ViewModal.CustomerContactModal.CustomerContactListModal
import com.aneriservices.app.ViewModal.FeedbackCodeModal.FeedBackCodeListModal
import com.aneriservices.app.ViewModal.FeedbackCodeModal.FeedBackCodeListModalFactory
import com.aneriservices.app.ViewModal.allocationMasterModal.allocationMasterFactoryModal
import com.aneriservices.app.ViewModal.allocationMasterModal.allocationMasterModal
import com.aneriservices.app.databinding.FragmentSyncDataBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class SyncDataFragment : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var sharedPreferencesService: SharedPreferencesService
    lateinit var binding: FragmentSyncDataBinding
    lateinit var allocationmasterModal: allocationMasterModal
    lateinit var appdatabase: appDatabase
    val currentDate = Utility.CrutSimpleDate.format(Date())
    private var snackbar: Snackbar? = null
    lateinit var feedBackCodeListModal: FeedBackCodeListModal
    lateinit var customerAddressListModal: CustomerAddressListModal
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var customerContactListModal: CustomerContactListModal
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSyncDataBinding.inflate(layoutInflater)

        sharedPreferencesService = SharedPreferencesService(requireActivity(), Utility.keyPrefs)

        val employeeId = sharedPreferencesService.getInt(Utility.keyEmployeeId)
        appdatabase = appDatabase.appDB(requireContext())

        val apiinteface = APIClient.getApiClientLogin().create(ApiInterface::class.java)
        val workingshetrepo = allocationMasterRepository(apiinteface, appdatabase)


        val feedbackcoderepo = FeedBackCodeListRepository(apiinteface, appdatabase)
        val customeraddressrepo = CustomerAddressGetListRepository(apiinteface, appdatabase)
        val customerContactrepo = CustomerContactRepository(apiinteface, appdatabase)



        setBottomMessage()

        binding.icBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController().popBackStack()
                    }
                })


        }



        binding.btnFeedbackcode.setOnClickListener {

            feedBackCodeListModal =
                ViewModelProvider(this, FeedBackCodeListModalFactory(feedbackcoderepo)).get(
                    FeedBackCodeListModal::class.java
                )

            val feedbacktime = binding.syncFeedbackTime.text
            if (feedbacktime == Utility.NeverSyncData) {
                syncfeedbackcode()
            } else {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle(Utility.TextSyncDilogTitle)
                alertDialogBuilder.setMessage(Utility.TextSyncDilogMessage)
                alertDialogBuilder.setPositiveButton(Utility.TextYes) { _, _ ->
                    syncfeedbackcode()
                }
                alertDialogBuilder.setNegativeButton(Utility.TextNo) { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

        }



        binding.btnCustomerdata.setOnClickListener {

            if (adddata == true) {
                allocationmasterModal =
                    ViewModelProvider(this, allocationMasterFactoryModal(workingshetrepo)).get(
                        allocationMasterModal::class.java
                    )
                if (snackbar?.isShown == true) {
                    snackbar?.dismiss()
                }

                val customertime = binding.syncCustormerTime.text
                if (customertime == Utility.NeverSyncData) {
                    employeeId?.let { it1 ->
                        allocationmasterBackground.startService(
                            requireContext(), it1
                        )
                    }
//                employeeId?.let { it1 -> fetchData(it1) }
                } else {
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle(Utility.TextSyncDilogTitle)
                    alertDialogBuilder.setMessage(Utility.TextSyncDilogMessage)
                    alertDialogBuilder.setPositiveButton(Utility.TextYes) { _, _ ->
//                    employeeId?.let { it1 -> fetchData(it1) }
                        employeeId?.let { it1 ->
                            allocationmasterBackground.startService(
                                requireContext(), it1
                            )
                        }

                    }
                    alertDialogBuilder.setNegativeButton(Utility.TextNo) { dialog, _ ->
                        dialog.dismiss()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
            } else {
                Log.e("##@TAG", "onCreateView: " + adddata)
                Log.e("##@TAG", "onCreateView: " + "hello")

                SnackbarService.snackbar(
                    requireView(), Utility.syncisundre, Snackbar.LENGTH_LONG
                )
            }
        }


        binding.btncustomerAddress.setOnClickListener {


            customerAddressListModal =
                ViewModelProvider(this, CustomerAddressFactoryModal(customeraddressrepo)).get(
                    CustomerAddressListModal::class.java
                )
            val feedbacktime = binding.textSyncaddress.text
            if (feedbacktime == Utility.NeverSyncData) {
                CustomerAddress(employeeId)
            } else {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle(Utility.TextSyncDilogTitle)
                alertDialogBuilder.setMessage(Utility.TextSyncDilogMessage)
                alertDialogBuilder.setPositiveButton(Utility.TextYes) { _, _ ->
                    CustomerAddress(employeeId)
                }
                alertDialogBuilder.setNegativeButton(Utility.TextNo) { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

        }


        binding.btncustomerContact.setOnClickListener {
                customerContactListModal =
                    ViewModelProvider(this, CustomerContactFactoryModal(customerContactrepo)).get(
                        CustomerContactListModal::class.java
                    )
                val feedbacktime = binding.textCustomerContact.text
                if (feedbacktime == Utility.NeverSyncData) {
                    CustomerContact(employeeId)
                } else {
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle(Utility.TextSyncDilogTitle)
                    alertDialogBuilder.setMessage(Utility.TextSyncDilogMessage)
                    alertDialogBuilder.setPositiveButton(Utility.TextYes) { _, _ ->
                        CustomerContact(employeeId)
                    }
                    alertDialogBuilder.setNegativeButton(Utility.TextNo) { dialog, _ ->
                        dialog.dismiss()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }


        }


        return binding.root
    }


    override fun onPause() {
        Log.e("##TAG", "onPause: " + "onpasuse")
        super.onPause()
    }

    private fun CustomerContact(employeeId: Int?): Unit? {
        appdatabase.CustomerContactMaster().allcustomerContactDelete()
        var page = 1
        val progressDialogView = layoutInflater.inflate(R.layout.progress_dialog_round, null)
        val progressBar = progressDialogView.findViewById<ProgressBar>(R.id.progressBar)
        val textViewMessage = progressDialogView.findViewById<TextView>(R.id.textViewMessage)
        val progressDialog =
            AlertDialog.Builder(requireContext()).setView(progressDialogView).setCancelable(false)
                .create()
        employeeId?.let { customerContactListModal.CustomerContactdata(it, page) }
        customerContactListModal.CustomerContactresult.removeObservers(viewLifecycleOwner)
        customerContactListModal.CustomerContactresult.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalRecords = it.data?.totalrecords ?: 0

                val totalPages = (totalRecords + Utility.pageSize - 1) / Utility.pageSize

                val progress = (page.toFloat() / totalPages.toFloat() * 100).toInt()


                progressBar.progress = progress
                val percentage = ((page.toFloat() / totalPages) * 100).toInt()
                textViewMessage.text = "$percentage%"
                progressDialog.setCancelable(false)

                page++
                if (page > totalPages) {

                    sharedPreferencesService.set(
                        Utility.keyCustomerContactSyncTime, currentDate
                    )

                    sharedPreferencesService.set(
                        Utility.keyTotalcustomerContact, totalRecords
                    )
                    setBottomMessage()

                    SnackbarService.snackbarr(
                        requireView(), Utility.SyncSuccessfully, Snackbar.LENGTH_LONG
                    )

                    progressDialog.dismiss()


                } else {
                    employeeId?.let { it1 ->
                        customerContactListModal.CustomerContactdata(
                            it1, page
                        )
                    }

                }

            }
        })
        progressDialog.show()
        return null
    }

    private fun CustomerAddress(employeeId: Int?): Unit? {
        appdatabase.CustomerAddressMaster().allcustomerAddressDelete()
        var page = 1
        val progressDialogView = layoutInflater.inflate(R.layout.progress_dialog_round, null)
        val progressBar = progressDialogView.findViewById<ProgressBar>(R.id.progressBar)
        val textViewMessage = progressDialogView.findViewById<TextView>(R.id.textViewMessage)
        val progressDialog =
            AlertDialog.Builder(requireContext()).setView(progressDialogView).setCancelable(false)
                .create()
        employeeId?.let { customerAddressListModal.CustomerAddressdata(it, page) }
        customerAddressListModal.CustomerAddressresult.removeObservers(viewLifecycleOwner)
        customerAddressListModal.CustomerAddressresult.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalRecords = it.data?.totalrecords ?: 0

                val totalPages = (totalRecords + Utility.pageSize - 1) / Utility.pageSize

                val progress = (page.toFloat() / totalPages.toFloat() * 100).toInt()


                progressBar.progress = progress
                val percentage = ((page.toFloat() / totalPages) * 100).toInt()
                textViewMessage.text = "$percentage%"
                progressDialog.setCancelable(false)

                page++
                if (page > totalPages) {

                    sharedPreferencesService.set(Utility.keyCustomerAddressSyncTime, currentDate)

                    sharedPreferencesService.set(
                        Utility.keyTotalcustomeraddress, totalRecords
                    )
                    setBottomMessage()

                    SnackbarService.snackbarr(
                        requireView(), Utility.SyncSuccessfully, Snackbar.LENGTH_LONG
                    )

                    progressDialog.dismiss()


                } else {
                    employeeId?.let { it1 ->
                        customerAddressListModal.CustomerAddressdata(
                            it1, page
                        )
                    }

                }

            }
        })
        progressDialog.show()
        return null
    }

    fun syncfeedbackcode() {

        Utility.showLoadingDialog(requireActivity())
        handler.postDelayed({
            val totalFeedbackCode = appdatabase.feedbackCodeMaster().getTotalCount()

            if (totalFeedbackCode == 0) {
                sharedPreferencesService.set(
                    Utility.keyTotalallocationmaster, totalFeedbackCode
                )
                feedBackCodeListModal.getlistFeedBackCodeList()
                feedBackCodeListModal.addFeedbackCodeListResult.observe(viewLifecycleOwner,
                    Observer { result ->
                        var total = result.data?.size
                        sharedPreferencesService.set(Utility.KeyTotalFeedbackCout, total)
                        sharedPreferencesService.set(Utility.KeyFeedBackCodeListTime, currentDate)
                        setBottomMessage()
                        Utility.dismissLoadingDialog()
                    })
            } else {

                appdatabase.feedbackCodeMaster().deleteAll()
                feedBackCodeListModal.getlistFeedBackCodeList()
                feedBackCodeListModal.addFeedbackCodeListResult.observe(viewLifecycleOwner,
                    Observer { result ->
                        var total = result.data?.size
                        sharedPreferencesService.set(Utility.KeyTotalFeedbackCout, total)

                        sharedPreferencesService.set(Utility.KeyFeedBackCodeListTime, currentDate)
                        setBottomMessage()
                        Utility.dismissLoadingDialog()
                    })
            }
            SnackbarService.snackbarr(
                requireView(), Utility.SyncSuccessfully, Snackbar.LENGTH_LONG
            )

        }, 3000)

    }


    @SuppressLint("MissingPermission")
    fun fetchData(employeeId: Int): Unit? {
        appdatabase.allocationMaster().allDelete()
        createNotificationChannel(requireContext())

        CoroutineScope(Dispatchers.Main).launch {
            var page = 1
            val notificationBuilder = NotificationCompat.Builder(requireContext(), "channel_id")
                .setSmallIcon(R.drawable.ic_notification).setContentTitle("Data Sync in Progress")
                .setProgress(100, 0, false).setOngoing(true)

            val notificationManager = NotificationManagerCompat.from(requireContext())
            notificationManager.notify(1, notificationBuilder.build())
            val progressDialogView = layoutInflater.inflate(R.layout.progress_dialog_round, null)
            val progressBar = progressDialogView.findViewById<ProgressBar>(R.id.progressBar)
            val textViewMessage = progressDialogView.findViewById<TextView>(R.id.textViewMessage)
            val progressDialog = AlertDialog.Builder(requireContext()).setView(progressDialogView)
                .setCancelable(false).create()

            CoroutineScope(Dispatchers.Main).launch {
                progressDialog.show()

                withContext(Dispatchers.IO) {
                    allocationmasterModal.getworkingshetdata(employeeId, page)
                }

                allocationmasterModal.workingsshetresult.removeObservers(viewLifecycleOwner)
                allocationmasterModal.workingsshetresult.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        val totalRecords = it.data?.totalrecords ?: 0
                        val totalPages = (totalRecords + Utility.pageSize - 1) / Utility.pageSize
                        val progress = (page.toFloat() / totalPages.toFloat() * 100).toInt()
                        notificationBuilder.setProgress(100, progress, false)


                        progressBar.progress = progress
                        val percentage = ((page.toFloat() / totalPages) * 100).toInt()
                        textViewMessage.text = "$percentage%"
                        notificationBuilder.setContentText("$percentage%")
                        notificationManager.notify(1, notificationBuilder.build())
                        progressDialog.setCancelable(true)

                        page++
                        if (page > totalPages) {
                            Log.e("##TAG", "fetchData: " + totalPages)
                            Log.e("##TAG", "fetchData: " + page)
                            Log.e("##TAG", "fetchData: " + "hello1")
                            sharedPreferencesService.set(Utility.keyDataSyncTime, currentDate)
                            sharedPreferencesService.set(
                                Utility.keyTotalallocationmaster, totalRecords
                            )
                            setBottomMessage()
                            SnackbarService.snackbarr(
                                requireView(), Utility.SyncSuccessfully, Snackbar.LENGTH_LONG
                            )
                            notificationManager.cancel(1)
                            progressDialog.dismiss()
                        } else {
                            Log.e("##TAG", "fetchData: " + "hello22")
                            coroutineScope.async {
                                withContext(Dispatchers.IO) {
                                    allocationmasterModal.getworkingshetdata(employeeId, page)
                                }
                            }

                        }
                    }
                })
            }
        }
        return null
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channelName = "Channel Name"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun setBottomMessage() {
        if (sharedPreferencesService.getString(Utility.keyDataSyncTime).isNullOrEmpty()) {
            binding.syncCustormerTime.text = "Never sync data"
        } else {
            binding.syncCustormerTime.text =
                "Last Sync at ${sharedPreferencesService.getString(Utility.keyDataSyncTime)}"
        }


        if (sharedPreferencesService.getString(Utility.keyCustomerAddressSyncTime)
                .isNullOrEmpty()
        ) {
            binding.textSyncaddress.text = "Never sync data"
        } else {
            binding.textSyncaddress.text =
                "Last Sync at ${sharedPreferencesService.getString(Utility.keyCustomerAddressSyncTime)}"

        }

        if (sharedPreferencesService.getString(Utility.keyCustomerContactSyncTime)
                .isNullOrEmpty()
        ) {
            binding.textCustomerContact.text = "Never sync data"
        } else {
            binding.textCustomerContact.text =
                "Last Sync at ${sharedPreferencesService.getString(Utility.keyCustomerContactSyncTime)}"

        }

        if (sharedPreferencesService.getString(Utility.KeyFeedBackCodeListTime).isNullOrEmpty()) {
            binding.syncFeedbackTime.text = "Never sync data"
        } else {
            binding.syncFeedbackTime.text =
                "Last Sync at ${sharedPreferencesService.getString(Utility.KeyFeedBackCodeListTime)}"
        }



        binding.totoalfeedbackcode.text =
            sharedPreferencesService.getInt(Utility.KeyTotalFeedbackCout)?.toString() ?: " "
//        binding.totalcount.text =
//            sharedPreferencesService.getInt(Utility.keyTotalallocationmaster)?.toString() ?: " "
        binding.totoaladdress.text =
            sharedPreferencesService.getInt(Utility.keyTotalcustomeraddress)?.toString() ?: " "
        binding.totoalContact.text =
            sharedPreferencesService.getInt(Utility.keyTotalcustomerContact)?.toString() ?: " "
        binding.totalcount.text = appdatabase.allocationMaster().getTotalCount().toString()
    }


}