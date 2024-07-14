package com.aneriservices.app.Fragment.Profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aneriservices.app.Adapter.SalarySlipListAdapterl
import com.aneriservices.app.ApiHelper.APIClient
import com.aneriservices.app.ApiHelper.ApiInterface
import com.aneriservices.app.Modal.masters.requestMasterModel
import com.aneriservices.app.R
import com.aneriservices.app.Repository.SallarySlip.GetSallarySlipListRepository
import com.aneriservices.app.Services.SharedPreferencesService
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.ViewModal.SalarySlipReason.GetSalarySlipFactoryModal
import com.aneriservices.app.ViewModal.SalarySlipReason.SalarySlipListModal
import com.aneriservices.app.databinding.FragmentSalaryslipListBinding


class SalarySlipListFragment : Fragment() {
    private var isApiCallMade = false
    lateinit var binding: FragmentSalaryslipListBinding
    lateinit var salarySlipListModal: SalarySlipListModal
    private lateinit var sharedPreferencesService: SharedPreferencesService
    var List = ArrayList<requestMasterModel>()
    lateinit var adapter: SalarySlipListAdapterl
    var page = 1
    var totalRecords = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSalaryslipListBinding.inflate(layoutInflater)

        sharedPreferencesService = SharedPreferencesService(
            requireActivity(), Utility.keyPrefs
        )
        Log.e("tag","onCreateView")
        List.clear()

        setupUI()
        observeViewModel()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
        return binding.root
    }

    private fun setupUI() {
        val employeeId = sharedPreferencesService.getInt(Utility.keyEmployeeId)
        binding.SalarySlipRequest.setOnClickListener {
            List.clear()
            findNavController().navigate(R.id.action_salarySlipListFragment_to_salarySlipRequestFragment)
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }


        adapter = SalarySlipListAdapterl(ArrayList())
        List.clear()
        binding.rcvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvList.adapter = adapter
        binding.setOnScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val totalItemCount = binding.setOnScroll.getChildAt(0).height
            val endReached = scrollY + binding.setOnScroll.height >= totalItemCount
            if (endReached && adapter.itemCount < totalRecords) {
                binding.progrebar.visibility = View.VISIBLE
                page++
                salarySlipListModal.getlistSalaryslip(employeeId, page)
            }
        })

    }

    private fun observeViewModel() {
        val apiInterface = APIClient.getApiClientLogin().create(ApiInterface::class.java)
        val repository = GetSallarySlipListRepository(apiInterface)
        salarySlipListModal = ViewModelProvider(
            this, GetSalarySlipFactoryModal(repository)
        ).get(SalarySlipListModal::class.java)
        val employeeId = sharedPreferencesService.getInt(Utility.keyEmployeeId)


        employeeId?.let { fetchSalarySlipList(it, page) }

        if (!isApiCallMade) {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.layoutWait.progebarlayout.visibility = View.GONE
                parentFragment?.viewLifecycleOwner?.let {

                }
            }, 3000)

            salarySlipListModal.addSalaryslipResult.observe(viewLifecycleOwner, Observer { response ->
                response.data?.data?.let { data ->
//                            if (List.size == 0){
//                                List = ArrayList(data)
//                            }else{
//
//                            }
                    Log.e("tag","data : ")
//                    List.clear()
                    List.addAll(data)
                    adapter.setData(List)
                    adapter.notifyDataSetChanged()
                    if (totalRecords == 0) {
                        totalRecords = response.data.totalrecords ?: 0
                    }
                    if (List.size >= totalRecords) {
                        binding.progrebar.visibility = View.GONE
                        return@Observer
                    }
                    if (data.size > 10) {
                        binding.progrebar.visibility = View.GONE

                    } else {
                        binding.progrebar.visibility = View.VISIBLE
                    }
                    if (adapter.itemCount == 0) {
                        binding.noDataTextView.visibility = View.VISIBLE
                    } else {
                        binding.noDataTextView.visibility = View.GONE
                    }
                }
            })
        } else {
            binding.layoutWait.progebarlayout.visibility = View.GONE
        }
    }
    private fun fetchSalarySlipList(employeeId: Int, page: Int) {
        salarySlipListModal.getlistSalaryslip(employeeId, page)
        binding.layoutWait.progebarlayout.visibility = View.VISIBLE
    }

}
