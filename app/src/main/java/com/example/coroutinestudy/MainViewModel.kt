package com.example.coroutinestudy

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class MainViewModel : ViewModel() {

    val repository = MainRepository()

    fun fetch() = liveData {
        repository.fetch()
            .onStart {
                println("==========start")
            }.catch {
                println("==========catch")
            }.onCompletion {
                println("==========onCompletion")
            }.collectLatest {
                emit(it)
            }
    }

    val livedata = MutableLiveData<Book>(Book("aaa", 22))

    fun test(): LiveData<Book> {
        val live = Transformations.map(livedata, object : Function<Book, Book> {
            override fun apply(input: Book): Book {
                input.age += 2
                return input
            }
        })
        return live
    }
}