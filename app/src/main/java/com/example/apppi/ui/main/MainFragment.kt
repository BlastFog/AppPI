package com.example.apppi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.apppi.MainActivity
import com.example.apppi.R

class MainFragment : Fragment() {
    private lateinit var startBut: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        startBut = view.findViewById(R.id.startBut)

        startBut.setOnClickListener{
            (activity as MainActivity).navigateToSecondFragment()
        }

        return view
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}