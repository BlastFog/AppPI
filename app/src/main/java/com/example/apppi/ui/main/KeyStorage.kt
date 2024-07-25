package com.example.apppi.ui.main

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import com.example.apppi.R
import com.example.apppi.data.DbManager

class KeyStorage : Fragment() {

    private lateinit var tableLayout: TableLayout
    private val passwordFields = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_key_storage, container, false)

        val storedKeys = DbManager.getInstance(requireContext()).getApiKeys()

        tableLayout = view.findViewById(R.id.apiKeyTableLayout)

        for(storedKey in storedKeys){
            addTableRow(storedKey.key, storedKey.value)
        }

        view.findViewById<Button>(R.id.addRowButton).setOnClickListener {
            val inputDialogView = LayoutInflater.from(context).inflate(R.layout.api_name_key_popup, null)

            val apiNameEditText = inputDialogView.findViewById<EditText>(R.id.apiNameInputField)
            val apiKEyEditText = inputDialogView.findViewById<EditText>(R.id.apiKeyInputField)

            val alertDialog = AlertDialog.Builder(requireContext())
                .setView(inputDialogView)
                .setTitle("Add API")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton("Submit") { _, _ ->
                    val apiName = apiNameEditText.text.toString()
                    val apiKey = apiKEyEditText.text.toString()
                    DbManager.getInstance(requireContext()).addApiKey(apiName, apiKey)
                    addTableRow(apiName, apiKey)
                }
                .create()
            alertDialog.show()
        }

        view.findViewById<ToggleButton>(R.id.toggleButton).setOnCheckedChangeListener { _, isChecked ->
            Log.d("KeyStorage", "Toggle button clicked: $isChecked")
            for(passwordField in passwordFields) {
                if (isChecked) {
                    passwordField.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                } else {
                    passwordField.transformationMethod =
                        PasswordTransformationMethod.getInstance()

                }
            }
        }

        return view
    }

    fun addTableRow(apiName: String, apiKey: String){
        val tableRow = TableRow(context).apply {
            layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        }

        val apiNameTextView = EditText(context).apply {
            setText(apiName)
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f)
        }

        val apiKeyTextView = EditText(context).apply {
            setText(apiKey)
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f)
            transformationMethod = PasswordTransformationMethod.getInstance()
        }

        passwordFields.add(apiKeyTextView)

        tableRow.addView(apiNameTextView)
        tableRow.addView(apiKeyTextView)

        tableLayout.addView(tableRow)
    }

    companion object {
        fun newInstance() = KeyStorage
    }
}