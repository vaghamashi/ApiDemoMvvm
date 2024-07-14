package com.aneriservices.app.Fragment.teamleader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aneriservices.app.Fragment.Dashboard.DashboardFragment
import com.aneriservices.app.Fragment.Dashboard.FieldexecutiveMainFragment
import com.aneriservices.app.Fragment.Dashboard.HomeFragment
import com.aneriservices.app.Fragment.Profile.ProfileFragment
import com.aneriservices.app.R
import com.aneriservices.app.databinding.FragmentTeamleaderMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TeamleaderMainFragment : Fragment() {

    lateinit var binding: FragmentTeamleaderMainBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTeamleaderMainBinding.inflate(layoutInflater)

        coroutineScope.launch {

            val pageAdapter = PageAdapterTL(childFragmentManager)
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
                    } else if (position.equals(1)) {
                        binding.bottomBar.itemActiveIndex = 1
                        binding.txtTitle.setText("Home")
                    } else {
                        binding.bottomBar.itemActiveIndex = 2
                        binding.txtTitle.setText("Profile")
                    }

                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })

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

        return  binding.root

    }
    class PageAdapterTL(fm: FragmentManager) : FragmentPagerAdapter(fm) {
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