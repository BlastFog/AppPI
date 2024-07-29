package com.example.apppi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.widget.ImageButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppi.network.CronetRequestBuilder
import com.example.apppi.R
import com.example.apppi.viewModels.CurrencyViewModel
import org.chromium.net.CronetEngine

class CurrencyExchangeFragment : Fragment() {

    private val availableCurrencies = mutableMapOf(
        "Australian Dollar" to "AUD",
        "Bulgarian Lev" to "BGN",
        "Brazilian Real" to "BRL",
        "Canadian Dollar" to "CAD",
        "Swiss Franc" to "CHF",
        "Chinese Renminbi Yuan" to "CNY",
        "Czech Koruna" to "CZK",
        "Danish Krone" to "DKK",
        "Euro" to "EUR",
        "British Pound" to "GBP",
        "Hong Kong Dollar" to "HKD",
        "Hungarian Forint" to "HUF",
        "Indonesian Rupiah" to "IDR",
        "Israeli New Sheqel" to "ILS",
        "Indian Rupee" to "INR",
        "Icelandic Króna" to "ISK",
        "Japanese Yen" to "JPY",
        "South Korean Won" to "KRW",
        "Mexican Peso" to "MXN",
        "Malaysian Ringgit" to "MYR",
        "Norwegian Krone" to "NOK",
        "New Zealand Dollar" to "NZD",
        "Philippine Peso" to "PHP",
        "Polish Złoty" to "PLN",
        "Romanian Leu" to "RON",
        "Swedish Krona" to "SEK",
        "Singapore Dollar" to "SGD",
        "Thai Baht" to "THB",
        "Turkish Lira" to "TRY",
        "United States Dollar" to "USD",
        "South African Rand" to "ZAR"
    )

    private val URL = "https://api.frankfurter.app/latest"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_currency_exchange, container, false)

        view.findViewById<ImageButton>(R.id.frankfurterButton).setOnClickListener {
            val visitFrankfurter = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.frankfurter.app/"))
            startActivity(visitFrankfurter)
        }

        val fromCurrencySpinner = view.findViewById<Spinner>(R.id.fromSpinner)
        fromCurrencySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            availableCurrencies.keys.toList()
        )

        val checkedCurrencies = BooleanArray(availableCurrencies.size)
        val targetCurrencyButton = view.findViewById<Button>(R.id.targetButton)
        var selectedString = ""
        targetCurrencyButton.setOnClickListener {
            val selectedCurrencies = mutableListOf<String>()

            AlertDialog.Builder(requireContext())
                .setTitle("Select Target Currencies")
                .setMultiChoiceItems(
                    availableCurrencies.keys.toList().toTypedArray(),
                    checkedCurrencies
                ) { _, _, _ ->
                }
                .setPositiveButton("OK") { _, _ ->
                    for (i in checkedCurrencies.indices) {
                        if (checkedCurrencies[i]) {
                            selectedCurrencies.add(availableCurrencies.getValue(availableCurrencies.keys.toList()[i]))
                        }
                    }
                    selectedString = selectedCurrencies.joinToString(",")
                }
                .show()
        }

        view.findViewById<Button>(R.id.currencyFetch).setOnClickListener {
            val cronetEngine: CronetEngine = CronetEngine.Builder(requireContext()).build()
            var amount = view.findViewById<TextView>(R.id.amount).text.toString()
            amount = if(amount.isEmpty()) "1" else amount

            val fromCurrency = view.findViewById<Spinner>(R.id.fromSpinner).selectedItem.toString()

            val params: Map<String, String> = mapOf(
                "amount" to amount,
                "from" to (availableCurrencies[fromCurrency] ?: "EUR"),
                "to" to selectedString
            )
            CronetRequestBuilder.newInstance()
                .buildRequest(cronetEngine, URL, params, API_NAME, this)
        }

        var myViewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)
        val resultTextView = view.findViewById<TextView>(R.id.resultTextView)
        myViewModel.rates.observe(context as LifecycleOwner, Observer { rates ->
            resultTextView.setText(rates.entries.joinToString("\n"))
        })

        return view
    }

    companion object {
        const val API_NAME = "Currency_Exchange"

        fun newInstance() = CurrencyExchangeFragment()
    }
}