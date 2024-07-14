package com.aneriservices.app.Fragment.Dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aneriservices.app.Adapter.CustomerAdapter
import com.aneriservices.app.DatabaseHelper.WorkingSheet.allocationMasterEntitty
import com.aneriservices.app.DatabaseHelper.WorkingSheet.appDatabase
import com.aneriservices.app.R
import com.aneriservices.app.Utility.LocationManagerUtil
import com.aneriservices.app.Utility.LocationManagerUtil.isLocationEnabled
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.Utility.utilities.CustomerItemClick
import com.aneriservices.app.Utility.utilities.SnackbarService
import com.aneriservices.app.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var appdatabase: appDatabase
    lateinit var customerAdapter: CustomerAdapter
    lateinit var coroutineScope: CoroutineScope
    var dialog: BottomSheetDialog? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitudeTextView: String? = null
    var longitudeTextView: String? = null
    private var selectedPosition: Int? = -1
    private var caseTotalCount = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        coroutineScope = CoroutineScope(Dispatchers.IO)


        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            isLocationEnabled(requireContext())
            appdatabase = appDatabase.appDB(requireContext())
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            caseTotalCount = countEntries()

            if (caseTotalCount == 0) {
                binding.nodaata.visibility = View.VISIBLE
                binding.setOnScroll.visibility = View.GONE
                binding.nodaata.setOnClickListener {
                    findNavController().navigate(R.id.action_mainFragment_to_syncDataFragment)
                }
            } else {
                binding.nodaata.visibility = View.GONE
                binding.setOnScroll.visibility = View.VISIBLE
            }


            initChips()
            initRecyclerView()

            initBackPressHandler()
            selectedPosition?.let { customerAdapter.setSelectedItemPosition(it) }
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String?): Boolean {
                    searchDatabase(newText.orEmpty())
                    Utility.hideKeyboard(requireActivity())
                    selectedPosition?.let { customerAdapter.setSelectedItemPosition(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        if (newText.isEmpty()) {
                            searchDatabase(null)
                            Utility.hideKeyboard(requireActivity())
                        }
                    } else {
                        Utility.hideKeyboard(requireActivity())
                    }

                    return false
                }
            })
        }






        return binding.root

    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        if (isLocationPermissionGranted()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latitudeTextView = location.latitude.toString()
                    longitudeTextView = location.longitude.toString()
                } else {

                    requestLocationUpdates()
                }
            }.addOnFailureListener { e ->
                Log.e("@@TAG", "Error getting last known location: ${e.message}")

            }
        } else {
            requestLocationPermission()
        }
    }

//    private fun resetSelectedItemBackground() {
//        selectedPosition?.let {
//            if (it != RecyclerView.NO_POSITION) {
//                customerAdapter.setSelectedItemPosition(RecyclerView.NO_POSITION)
//                selectedPosition = RecyclerView.NO_POSITION
//            }
//        }
//    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    latitudeTextView = location.latitude.toString()
                    longitudeTextView = location.longitude.toString()
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun searchDatabase(query: String?) {
        coroutineScope.launch {
            val searchData = withContext(Dispatchers.IO) {
                appDatabase.appDB(requireContext()).allocationMaster().getdata(query, 0)
            }
            withContext(Dispatchers.Main) {
                customerAdapter.getnewdata(searchData)
                if (query.isNullOrEmpty()) {
                    val allData =
                        appDatabase.appDB(requireContext()).allocationMaster().getdata(null, 0)
                    customerAdapter.getnewdata(allData)
                }
            }
        }
    }


    fun initChips() {
        val chipNames = listOf(
            "All",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5",
            "Item 6",
            "Item 7",
            "Item 8",
            "Item 9",
            "Item 10"
        )
        for (name in chipNames) {
            val chip = Chip(requireContext())
            chip.chipCornerRadius = resources.getDimension(R.dimen.your_corner_radius)
            chip.text = name
            chip.isClickable = true
            chip.isCheckable = true
            binding.chpKeyword.addView(chip)
        }
    }

    private fun initRecyclerView() {
        var page = 0

        dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.customerfeedback, null)
        var itemclicke = object : CustomerItemClick {
            @SuppressLint("MissingPermission", "ResourceAsColor")
            override fun onTap(position: String, item: allocationMasterEntitty, clickedView: View) {

                if (isLocationEnabled(requireContext())) {
                    val custNameTextView = view.findViewById<TextView>(R.id.cust_name)
                    custNameTextView.apply {
                        text = Utility.getFormettedString(item.customerName)
                        maxLines = 2
                        ellipsize = TextUtils.TruncateAt.END
                    }
                } else {
                    LocationManagerUtil.requestLocationSettings(
                        requireActivity(), LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
                view.findViewById<TextView>(R.id.btn_visit).setOnClickListener {

                    if (isLocationEnabled(requireContext())) {
                        fetchLocation()
                        Utility.showLoadingDialog(requireActivity())
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.searchView.setQuery("", null == true)

                            val bundle = Bundle().apply {
                                putSerializable(Utility.KeyallocationMasterEntity, item)
                                putString(Utility.Keylatitude, latitudeTextView)
                                putString(Utility.Keylongitude, longitudeTextView)

                            }

                            if (isLocationPermissionGranted()) {
                                try {
                                    findNavController().navigate(
                                        R.id.action_mainFragment_to_visitFeedBackFragment, bundle
                                    )
                                    dialog?.dismiss()
                                }catch (e:Exception){

                                    Log.e("@@TAG", "onTap: "+"heloo" )
                                }

                            } else {
                                LocationManagerUtil.requestLocationSettings(
                                    requireActivity(), LOCATION_PERMISSION_REQUEST_CODE
                                )
                            }

                            Utility.dismissLoadingDialog()


                        }, 2000)


                    } else {

                        dialog?.dismiss()
                        LocationManagerUtil.requestLocationSettings(
                            requireActivity(), LOCATION_PERMISSION_REQUEST_CODE
                        )
                    }
                }


                view.findViewById<TextView>(R.id.cut_trackerno).text =
                    "Tracking No.: ${item.trackingNo}"
                view.findViewById<LinearLayout>(R.id.idBtnDismiss).setOnClickListener {
                    dialog?.dismiss()
                }
                dialog?.setCancelable(true)
                dialog?.setContentView(view)
                dialog?.show()
            }

        }


        customerAdapter = CustomerAdapter(
            itemclicke, requireContext()
        )

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            customerAdapter.addItem(coroutineScope.async(Dispatchers.IO) {
                appdatabase.allocationMaster().getdata(null, page)
            }.await())

        }






        binding.rcvList.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

        binding.rcvList.adapter = customerAdapter

        binding.setOnScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                val totalItemCount = binding.setOnScroll.getChildAt(0).height
                val endReached = scrollY + binding.setOnScroll.height >= totalItemCount
                if (endReached && !v.canScrollVertically(scrollX)) {
                    Log.e("tag", "endReached && !v.canScrollVertically(scrollX)")
                    binding.progrebar.visibility = View.VISIBLE
                    page += 10
                    val newData = coroutineScope.async(Dispatchers.IO) {
                        appdatabase.allocationMaster().getdata(null, page)
                    }.await()
                    customerAdapter.updateData(newData)

                    Log.e("tag", "endReached && !v.canScrollVertically(scrollX) end")
                } else if (page > caseTotalCount) {
                    Log.e("tag", "page > caseTotalCount")
                    binding.progrebar.visibility = View.GONE
                    SnackbarService.snackbar(
                        requireView(), Utility.allDatashow, Snackbar.LENGTH_LONG
                    )

                }
            }
        })
    }


    override fun onPause() {
        coroutineScope.cancel()
        super.onPause()
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }


    private fun initBackPressHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    private suspend fun countEntries(): Int {
        return withContext(Dispatchers.IO) {
            appdatabase.allocationMaster().getTotalCount()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation()
                } else {

                    Log.e("@@TAG", "Location permission denied")
                }
            }
        }
    }


}






