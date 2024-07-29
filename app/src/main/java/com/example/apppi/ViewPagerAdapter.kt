package com.example.apppi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apppi.ui.main.Fragment2
import com.example.apppi.ui.main.Fragment3
import com.example.apppi.ui.main.CurrencyExchangeFragment
import com.example.apppi.ui.main.KeyStorage

class ViewPagerAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf(
        KeyStorage(),
        Fragment2(),
        Fragment3(),
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