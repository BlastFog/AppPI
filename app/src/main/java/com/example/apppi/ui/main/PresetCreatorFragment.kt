package com.example.apppi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.apppi.MainActivity
import com.example.apppi.R
import com.example.apppi.ViewPagerAdapter


class PresetCreatorFragment : Fragment() {

    private lateinit var createBut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_preset_creator, container, false)

        createBut = view.findViewById(R.id.presetCreateBut)
        createBut.setOnClickListener{
            (activity as MainActivity).navigateBackAndAddFragment(CustomFragment.newInstance("XYZ","",true,true,""))
        }

        return view
    }

    companion object {
        fun newInstance() = PresetCreatorFragment()
    }
}