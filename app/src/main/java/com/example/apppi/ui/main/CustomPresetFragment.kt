package com.example.apppi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

import com.example.apppi.R


class CustomPresetFragment() : Fragment() {

    private lateinit var fragName : String
    private lateinit var url : String
    private var raw : Boolean = false
    private var key : Boolean = false
    private lateinit var nested : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            fragName = it.getString(ARG_PARAM1).toString()
            url = it.getString(ARG_PARAM2).toString()
            raw = it.getBoolean(ARG_PARAM3)
            key = it.getBoolean(ARG_PARAM4)
            nested = it.getString(ARG_PARAM5).toString()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_custom, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.customToolbar)
        toolbar.title = fragName.plus(" API")

        return view
    }

    companion object {
        private const val ARG_PARAM1 = "name"
        private const val ARG_PARAM2 = "url"
        private const val ARG_PARAM3 = "raw"
        private const val ARG_PARAM4 = "key"
        private const val ARG_PARAM5 = "nested"

        fun newInstance(name : String, url : String, raw : Boolean, key : Boolean, nested : String) : CustomPresetFragment{
            val fragment = CustomPresetFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, name)
            args.putString(ARG_PARAM2, url)
            args.putBoolean(ARG_PARAM3, raw)
            args.putBoolean(ARG_PARAM4, key)
            args.putString(ARG_PARAM5, nested)
            fragment.arguments = args

            return fragment
        }
    }

}