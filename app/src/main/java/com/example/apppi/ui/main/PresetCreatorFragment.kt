package com.example.apppi.ui.main

import DbHelper
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.apppi.data.DbManager
import com.example.apppi.data.FragmentDataObject


class PresetCreatorFragment : Fragment() {

    private lateinit var createBut: Button
    private lateinit var backBut: Button

    private lateinit var nameField: EditText
    private lateinit var urlField: EditText
    private lateinit var rawField: CheckBox
    private lateinit var keyField: CheckBox
    private lateinit var nestedField: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preset_creator, container, false)

        nestedField = view.findViewById(R.id.presetNestedField)
        rawField = view.findViewById(R.id.checkRaw)

        // raw json should not be selectable if a nested attribute is searched
        nestedField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.isNullOrEmpty()) {
                    rawField.isEnabled = true
                    rawField.isChecked = true
                }else {
                    rawField.isEnabled = false
                    rawField.isChecked = false
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        createBut = view.findViewById(R.id.presetCreateBut)
        backBut = view.findViewById(R.id.presetBackBut)
        createBut.setOnClickListener{

            nameField = view.findViewById(R.id.presetNameField)
            urlField = view.findViewById(R.id.presetUrlField)

            if(nameField.text.isEmpty() || urlField.text.isEmpty()){
                (activity as MainActivity).fieldEmptyToast()
            }else {
                keyField = view.findViewById(R.id.checkKey)
                Log.v("debugFields", rawField.isChecked.toString())

                val fragment = FragmentDataObject(
                    name = nameField.text.toString(),
                    url = urlField.text.toString(),
                    raw = rawField.isChecked,
                    key = keyField.isChecked,
                    nested = nestedField.text.toString(),
                    queries = ""
                )

                DbManager.getInstance(requireContext()).addFragment(fragment)

                (activity as MainActivity).navigateBackAndAddFragment(
                    CustomPresetFragment.newInstance(
                        fragment
                    )
                )
            }
        }
        backBut.setOnClickListener{
            (activity as MainActivity).navigateBack()
        }
        return view
    }

    companion object {
        fun newInstance() = PresetCreatorFragment()
    }
}