package com.aneriservices.app.Fragment.Profile

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aneriservices.app.Adapter.SalarySlipAdapter
import com.aneriservices.app.ApiHelper.APIClient
import com.aneriservices.app.ApiHelper.ApiInterface
import com.aneriservices.app.Modal.Auth.AddSalarySlipdataModal
import com.aneriservices.app.Modal.comanModal
import com.aneriservices.app.R
import com.aneriservices.app.Repository.SallarySlip.AddSalarySlipRepository
import com.aneriservices.app.Repository.SallarySlip.SalarySlipReasonRepository
import com.aneriservices.app.Services.SharedPreferencesService
import com.aneriservices.app.Utility.InternetConnectionChecker
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.Utility.utilities.ItemClick
import com.aneriservices.app.Utility.utilities.SnackbarService
import com.aneriservices.app.ViewModal.SalarySlipReason.AddSalarySlipModaFactory
import com.aneriservices.app.ViewModal.SalarySlipReason.AddSalarySlipModal
import com.aneriservices.app.ViewModal.SalarySlipReason.SalarySlipReasonModal
import com.aneriservices.app.ViewModal.SalarySlipReason.salarySlipReasonModalFactory
import com.aneriservices.app.databinding.FragmentSalarySlipRequestBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SalarySlipRequestFragment : Fragment() {
    lateinit var salarySlipReasonModal: SalarySlipReasonModal
    lateinit var binding: FragmentSalarySlipRequestBinding
    lateinit var adapter: SalarySlipAdapter
    var List = ArrayList<comanModal>()
    private var isApiCallMade = false
    private var addother = false
    lateinit var addSalarySlipModal: AddSalarySlipModal
    lateinit var addSalaryslipdataModal: AddSalarySlipdataModal
    private lateinit var sharedPreferencesService: SharedPreferencesService
    lateinit var datareson: String
    private var selectedChipCount = 0
    private var dialog: BottomSheetDialog? = null
    var isDialogShowing = false
    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSalarySlipRequestBinding.inflate(layoutInflater)

        view?.let { InternetConnectionChecker(requireContext(), it) }

        val addapiInterface = APIClient.getApiClientLogin().create(ApiInterface::class.java)
        val addrepository = AddSalarySlipRepository(addapiInterface)

        addSalarySlipModal = ViewModelProvider(
            this, AddSalarySlipModaFactory(addrepository)
        )[AddSalarySlipModal::class.java]

        val last15Months = getLast15Months()
        last15Months.forEachIndexed { index, month ->
            val chip = Chip(requireContext())
            chip.text = month
            chip.isCheckable = true
            chip.id = index
            binding.chipGroup.addView(chip)
        }
        val apiInterface = APIClient.getApiClientLogin().create(ApiInterface::class.java)
        val repository = SalarySlipReasonRepository(apiInterface)


        for (i in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(i) as Chip
            chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
            chip.setChipIconTintResource(R.color.titlebar)
            chip.chipMinHeight = resources.getDimension(R.dimen.margin)
            chip.chipIconSize = resources.getDimension(R.dimen.icon_size)

            chip.chipCornerRadius = resources.getDimension(R.dimen.your_corner_radius)
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    if (selectedChipCount >= 3) {
                        chip.isChecked = false

                        SnackbarService.snackbar(
                            requireView(), Utility.MaximumAdd3Months, Snackbar.LENGTH_LONG
                        )

                    } else {
                        selectedChipCount++
                        chip.setChipIcon(
                            ContextCompat.getDrawable(
                                requireContext(), R.drawable.ic_right
                            )
                        )
                    }
                } else {
                    selectedChipCount--
                    chip.setChipIcon(null)
                }
            }


            binding.icBack.setOnClickListener {
                findNavController().popBackStack()
            }
            salarySlipReasonModal = ViewModelProvider(
                this, salarySlipReasonModalFactory(repository)
            )[SalarySlipReasonModal::class.java]
            sharedPreferencesService = SharedPreferencesService(
                requireActivity(), Utility.keyPrefs
            )
            binding.btnReason.setOnClickListener {
                fetchData()
                dialog = BottomSheetDialog(requireContext())
                val view = layoutInflater.inflate(R.layout.reason_sheet_dialog, null)
                val btnClose = view.findViewById<LinearLayout>(R.id.idBtnDismiss)
                val rcvList = view.findViewById<RecyclerView>(R.id.rcvlist)
                var clickItem = object : ItemClick {
                    override fun onTap(i: String) {
                        if (i.equals("Other")) {
                            binding.editReson.setText("")
                            binding.editReson.setHint("other")
                            binding.editReson.isEnabled = true
                        } else {

                            datareson = binding.editReson.setText(i).toString()
                            binding.editReson.isEnabled = false
                        }

                        dialog?.dismiss()
                        isDialogShowing = false

                    }
                }


                adapter = SalarySlipAdapter(List, clickItem)
                if (!addother) {
                    val additionalName = "Other"
                    adapter.addName(additionalName)
                    addother = true
                }
                rcvList.layoutManager = LinearLayoutManager(requireContext())
                rcvList.adapter = adapter
                btnClose.setOnClickListener {
                    dialog?.dismiss()
                    isDialogShowing = false
                }
                dialog?.setCancelable(true)
                dialog?.setContentView(view)
                dialog?.show()
                isDialogShowing = true

            }

            binding.saveBtn.setOnClickListener {
                val text = binding.editReson.text.toString()

                val checkedChipIds = binding.chipGroup.checkedChipIds


                if (checkedChipIds.isEmpty()) {

                    SnackbarService.snackbar(
                        requireView(), Utility.PleaseSelectAtOnrmonths, Snackbar.LENGTH_LONG
                    )

                } else if (text.isEmpty()) {
                    SnackbarService.snackbar(
                        requireView(), Utility.PleaseSelectReson, Snackbar.LENGTH_LONG
                    )
                } else {
                    binding.layoutWait.progebarlayout.visibility = View.VISIBLE
                    val selectedMonths = mutableListOf<String>()
                    for (chipId in checkedChipIds) {
                        val chip = binding.chipGroup.findViewById<Chip>(chipId)
                        selectedMonths.add(chip.text.toString())
                    }
                    val selectedMonthsString = selectedMonths.joinToString(", ")
                    val id = sharedPreferencesService.getInt(Utility.keyEmployeeId)



                    addSalaryslipdataModal = AddSalarySlipdataModal(id, selectedMonthsString, text)
                    addSalaryslipdataModal.employeeId?.let { it1 ->
                        addSalarySlipModal.addSalaryslip(
                            it1,
                            addSalaryslipdataModal.months,
                            addSalaryslipdataModal.purpose,

                            )
                    }

                    addSalarySlipModal.addSalaryslipResult.observe(viewLifecycleOwner, Observer {
                        it.message?.let { message ->
                            if (message.contains("Timeout occurred", ignoreCase = true)) {
                                SnackbarService.snackbar(
                                    requireView(), message, Snackbar.LENGTH_LONG
                                )
                                binding.layoutWait.progebarlayout.visibility = View.GONE
                            } else {
                                findNavController().popBackStack()
                                SnackbarService.snackbar(
                                    requireView(), message, Snackbar.LENGTH_LONG
                                )
                            }
                        }
                    })
                }
            }

        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        return binding.root
    }


    private fun fetchData() {
        if (!isApiCallMade) {
            salarySlipReasonModal.salarySlipReasonResult.observe(
                viewLifecycleOwner,
                Observer { result ->
                    if (result != null && result.data != null) {
                        var data = result.data as List<comanModal>
                        List.addAll(data)
                    } else {

                    }


                })
            isApiCallMade = true
        }
    }

    private fun getLast15Months(): List<String> {
        val monthsList = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MMM yy", Locale.getDefault())

        for (i in 0 until 15) {
            calendar.add(Calendar.MONTH, -1)
            val monthYearString = dateFormat.format(calendar.time)
            val monthAbbreviation = monthYearString.substring(0, 3).toUpperCase(Locale.getDefault())
            val yearDigits = monthYearString.substring(3)
            monthsList.add("$monthAbbreviation $yearDigits")
        }

        return monthsList
    }


}