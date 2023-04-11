package com.example.coroutinestudy

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.IllegalArgumentException
import kotlin.concurrent.thread

fun main() {

    runBlocking {
        flow<String>{
            throw IllegalArgumentException("")
        }
            .onStart {
                println("===== onStart")
            }.onCompletion {
                println("=====onCompletion")
            }.onEmpty {
                println("=====onEmpty")
            }.catch {
                println("=====catch")
            }.
            collect {
                println("===== " + it)
            }
    }
}