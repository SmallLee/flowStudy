package com.example.coroutinestudy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart


suspend fun <T> BaseViewModel.launchFlow(
    showLoading: Boolean = true,
    isToastError: Boolean = true, request: suspend () -> BaseResponse<T>
): Flow<BaseResponse<T>> {
    if (showLoading) {
        // loading
    }
    return flow {
        val response = invokeFunction(request)
        if (response.isSuccess()) {
            emit(response)
        } else {
            // error
            if (isToastError) {
                // toast error
            }
        }
    }.onStart {

    }.flowOn(Dispatchers.IO)
}

private suspend fun <T> invokeFunction(function: suspend () -> BaseResponse<T>): BaseResponse<T> {
    val response: BaseResponse<T> =
        try {
            function.invoke()
        } catch (e: Exception) {
            BaseResponse(-1)
        }
    return response
}
