package com.example.coroutinestudy

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    fun login(){

        viewModelScope.launch {
            launchFlow(false,false){
                Api.getListProject()
            }
        }
    }
}