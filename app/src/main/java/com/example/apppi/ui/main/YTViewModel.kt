package com.example.apppi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class YTViewModel : ViewModel() {
    private val _subs = MutableLiveData<Int>()
    val subs: LiveData<Int> get() = _subs
    private val _channelID = MutableLiveData<String>()
    val channelID : LiveData<String> get() = _channelID

    fun setSubs(subscribers : Int){
        _subs.postValue(subscribers)
    }
    fun setID(chID : String){
        _channelID.postValue(chID)
        //_channelID.value = chID
    }
}