package com.example.apppi.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppi.network.CurrencyApiResponse
import com.google.gson.Gson

class CurrencyViewModel : ViewModel() {
    private val _rates = MutableLiveData<Map<String, Double>>()
    val rates: LiveData<Map<String, Double>> get() = _rates

    fun setRates(json : String){
        val gson = Gson()
        val data = gson.fromJson(json, CurrencyApiResponse::class.java)
        _rates.postValue(data.rates)
    }
}