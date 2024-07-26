package com.example.apppi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CustomPresetViewModel : ViewModel() {
    private val _jsonObject = MutableLiveData<String>()
    val jsonObject: LiveData<String> get() = _jsonObject

    private val _jsonAttribute = MutableLiveData<String>()
    val jsonAttribute: LiveData<String> get() = _jsonAttribute

    fun setJsonObject(jsObj : String){
        _jsonObject.postValue(jsObj)
    }
    fun setJsonAttribute(jsObj : String){
        _jsonAttribute.postValue(jsObj)
    }

}