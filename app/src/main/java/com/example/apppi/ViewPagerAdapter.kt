package com.example.apppi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apppi.ui.main.Fragment2
import com.example.apppi.ui.main.Fragment3
import com.example.apppi.ui.main.Fragment4

class ViewPagerAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = listOf(
        Fragment2(),
        Fragment3(),
        Fragment4()
    )

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}