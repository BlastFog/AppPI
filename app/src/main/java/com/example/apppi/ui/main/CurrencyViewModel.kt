package com.example.apppi.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class CurrencyViewModel : ViewModel() {
    private val _rates = MutableLiveData<Map<String, Double>>()
    val rates: LiveData<Map<String, Double>> get() = _rates

    fun setRates(json : String){
        val map = mutableMapOf<String, Double>()
        val gson = Gson()
        val data = gson.fromJson(json, CurrencyApiResponse::class.java)

        Log.d("CurrencyViewModel", "First: " + data.rates.toString())

        _rates.postValue(data.rates)
    }
}