package com.example.coroutinestudy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.internal.FlowLayout

open class BaseViewModel : ViewModel(), IViewModel {

    var loadingEvent = MutableLiveData<Boolean>()
    override fun showLoading() {
        loadingEvent.value = true
    }

    override fun hideLoading() {
        loadingEvent.value = false
    }
}