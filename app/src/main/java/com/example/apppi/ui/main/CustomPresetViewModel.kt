package com.example.apppi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CustomPresetViewModel : ViewModel() {
    private val _jsonObject = MutableLiveData<String>()
    val jsonObject: LiveData<String> get() = _jsonObject

    fun setJsonObject(jsObj : String){
        _jsonObject.postValue(jsObj)
    }
}