package com.example.coroutinestudy

class BaseResponse<T>(val code: Int) {

    private val data: T? = null;

    fun isSuccess() = code == 0
}