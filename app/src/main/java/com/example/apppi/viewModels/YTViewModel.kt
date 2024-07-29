package com.example.apppi.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class YTViewModel : ViewModel() {

    private val _attributes = MutableLiveData<List<String>>()
    val attributes: LiveData<List<String>> get() = _attributes

    private val _channelID = MutableLiveData<String>()
    val channelID : LiveData<String> get() = _channelID

    fun setAttributes(att : List<String>){
        _attributes.postValue(att)
    }

    fun setID(chID : String){
        _channelID.postValue(chID)
    }
}