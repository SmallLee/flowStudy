package com.example.coroutinestudy

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn

class MainRepository {


    suspend fun fetch(): MutableSharedFlow<String> {
        val f = MutableSharedFlow<String>()
        return f
    }
}