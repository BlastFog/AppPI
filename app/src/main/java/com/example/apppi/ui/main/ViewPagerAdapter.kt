package com.example.apppi.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf(
        KeyStorageFragment(),
        IntroductoryFragment(),
        YTFragment(),
        CurrencyExchangeFragment()
    )

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]

    fun addFragment(frag : Fragment){
        fragments.add(frag)
    }

    fun removeFragment(frag : Fragment){
        fragments.remove(frag)
    }

}