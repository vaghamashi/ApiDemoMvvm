package com.aneriservices.app.Fragment.Dashboard

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.aneriservices.app.Adapter.MyPagerAdapter
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.databinding.FragmentDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DashboardFragment : Fragment() {

    var dialog: BottomSheetDialog? = null
    lateinit var binding: FragmentDashboardBinding
    private val handler = Handler()
    private val autoScrollRunnable = Runnable {
        val nextItem = (binding.viewPager.currentItem + 1) % (binding.viewPager.adapter?.count ?: 1)
        binding.viewPager.setCurrentItem(nextItem, true)
        startAutoScroll()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog = BottomSheetDialog(requireContext())
        dialog?.dismiss()
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        Utility.hideKeyboard(requireActivity())

        val itemsList = listOf("Allocation", "POS", "CUR Bal/OS", "Min Bal/OD", "RB", "Total Paid")

        val adapter = MyPagerAdapter(itemsList, requireContext())
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.setViewPager(binding.viewPager)


        startAutoScroll()



        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    binding.viewPager.adapter =  null
                    Log.e("##TAG", "handleOnBackPressed: " + "heloo")
                    activity?.finishAffinity()
                }


            })


        return binding.root
    }

    private fun startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, 5000)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
    }
}