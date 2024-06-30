package com.example.apppi.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.apppi.MainActivity
import com.example.apppi.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var startBut: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        startBut = view.findViewById(R.id.startBut)
        startBut.setOnClickListener{
            //parentFragmentManager.beginTransaction().replace(R.id.fragment_container,Fragment2.newInstance()).addToBackStack(null).commit()
            (activity as MainActivity).navigateToSecondFragment()
        }

        return view
    }
}