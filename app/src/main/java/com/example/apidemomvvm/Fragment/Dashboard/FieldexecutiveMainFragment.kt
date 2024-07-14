package com.aneriservices.app.Fragment.Dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aneriservices.app.Fragment.Profile.ProfileFragment
import com.aneriservices.app.R
import com.aneriservices.app.Utility.Utility
import com.aneriservices.app.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FieldexecutiveMainFragment : Fragment() {


    lateinit var binding: FragmentMainBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        coroutineScope.launch {

            val pageAdapter = PageAdapter(childFragmentManager)
            binding.viewPager.adapter = pageAdapter


            binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {

                    if (position.equals(0)) {
                        binding.bottomBar.itemActiveIndex = 0
                        binding.txtTitle.setText("Dashboard")
                        Utility.hideKeyboard(requireActivity())
                        binding.btnMore.setVisibility(View.GONE)

                    } else if (position.equals(1)) {
                        binding.bottomBar.itemActiveIndex = 1
                        binding.txtTitle.setText("Home")
                        Utility.hideKeyboard(requireActivity())
                        binding.btnMore.setVisibility(View.VISIBLE)
                    } else {
                        binding.bottomBar.itemActiveIndex = 2
                        binding.txtTitle.setText("Profile")
                        Utility.hideKeyboard(requireActivity())
                        binding.btnMore.setVisibility(View.GONE)
                    }

                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })

            binding.btnMore.setOnClickListener { view ->
                val popupMenu = PopupMenu(requireContext(), view)

                popupMenu.inflate(R.menu.menu_home)

                popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                    when (menuItem.itemId) {
                        R.id.action_settings -> {
                            true
                        }

                        R.id.action_soon -> {
                            true
                        }

                        else -> false
                    }
                }

                popupMenu.show()
            }

            binding.bottomBar.setOnItemSelectedListener { position ->
                if (position.equals(0)) {
                    binding.viewPager.currentItem = 0
                } else if (position.equals(1)) {
                    binding.viewPager.currentItem = 1
                } else {
                    binding.viewPager.currentItem = 2

                }
            }
        }
        return binding.root
    }

    class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 3;
        }

        override fun getItem(position: Int): Fragment {

            when (position) {
                0 -> {
                    return DashboardFragment()
                }

                1 -> {
                    return HomeFragment()
                }

                else -> {
                    return ProfileFragment()
                }
            }
        }


    }


}