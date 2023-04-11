package com.example.coroutinestudy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(), IViewModel {

    var loadingEvent = MutableLiveData<Boolean>()
    override fun showLoading() {
        loadingEvent.value = true
    }

    override fun hideLoading() {
        loadingEvent.value = false
    }
}