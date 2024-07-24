package com.example.apppi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.apppi.MainActivity
import com.example.apppi.R
import com.example.apppi.ViewPagerAdapter


class PresetCreatorFragment : Fragment() {

    private lateinit var createBut: Button

    private lateinit var nameField: EditText
    private lateinit var urlField: EditText
    private lateinit var rawField: CheckBox
    private lateinit var keyField: CheckBox
    private lateinit var nestedField: EditText

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
            nameField = view.findViewById(R.id.presetNameField)
            urlField = view.findViewById(R.id.presetUrlField)
            rawField = view.findViewById(R.id.checkRaw)
            keyField = view.findViewById(R.id.checkKey)
            nestedField = view.findViewById(R.id.presetNestedField)

            Log.v("debugFields", rawField.isChecked.toString())

            (activity as MainActivity).navigateBackAndAddFragment(CustomFragment.newInstance(nameField.text.toString(),urlField.text.toString(),rawField.isChecked,keyField.isChecked,nestedField.text.toString()))
        }

        return view
    }

    companion object {
        fun newInstance() = PresetCreatorFragment()
    }
}