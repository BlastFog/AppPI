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
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.apppi.R
import com.example.apppi.data.DbManager

class KeyStorage : Fragment() {

    private val ALTERNATIVE_METHOD = "Use alternative authentication method"
    private lateinit var tableLayout: TableLayout
    private val passwordFields = mutableListOf<EditText>()
    private lateinit var biometricPrompt: BiometricPrompt
    private var isAuthenticated = false

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

        for (storedKey in storedKeys) {
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


        biometricPrompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(requireContext()),
            object: BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {

                    if(errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON){
                        AlertDialog.Builder(requireContext())
                            .setTitle("Alternative method :)")
                            .setPositiveButton("Show anyway") { _, _ ->
                                showKeys()
                            }
                            .create()
                            .show()
                    }

                    Log.d("FINGERPRINT", "Something went wrong: $errString")
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    Log.d("FINGERPRINT", "Authentication succeeded")
                    showKeys()
                }

                override fun onAuthenticationFailed() {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Provide your fingerprint to access your API Keys")
            .setNegativeButtonText(ALTERNATIVE_METHOD)
            .build()

        view.findViewById<ToggleButton>(R.id.toggleButton).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if(isAuthenticated)
                    showKeys()
                else {
                    biometricPrompt.authenticate(promptInfo)
                    isAuthenticated = true
                }
            } else {
                hideKeys()
            }
        }
        return view
    }

    fun hideKeys() {
        Log.d("Fingerprint", "Hiding keys")
        for (passwordField in passwordFields) {
            passwordField.apply {
                isFocusableInTouchMode = false
                isCursorVisible = false
                focusable = View.NOT_FOCUSABLE
                transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    fun showKeys() {
        Log.d("Fingerprint", "Showing keys")
        for (passwordField in passwordFields) {
            passwordField.apply {
                isFocusableInTouchMode = true
                focusable = View.FOCUSABLE
                isCursorVisible = true
                transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }
    }

    fun addTableRow(apiName: String, apiKey: String) {
        val tableRow = TableRow(context).apply {
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
        }

        val apiNameTextView = TextView(context).apply {
            text = apiName
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f)
        }

        val apiKeyTextView = EditText(context).apply {
            setText(apiKey)
            focusable = View.NOT_FOCUSABLE
            isCursorVisible = false
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f)
            transformationMethod = PasswordTransformationMethod.getInstance()
            addTextChangedListener {
                DbManager.getInstance(requireContext()).updateApiKey(apiName, text.toString())
            }

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