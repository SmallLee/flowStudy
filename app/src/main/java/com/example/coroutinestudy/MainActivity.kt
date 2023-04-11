package com.example.coroutinestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val originData = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val mapData = Transformations.map(originData) {
        //  it.plus("word ")
        //}
        /*val mapData = originData.map {
            it.length
        }*/
        /*val mapData = Transformations.map(originData, object : Function<String, Int> {
            override fun apply(input: String?): Int {
                Log.d("===", "apply: " + input)
                return input?.length ?: 0
            }
        })*/
        /*val mapData = Transformations.switchMap(originData, object : Function<String, LiveData<String>> {
            override fun apply(input: String?): LiveData<String> {

                return originData
            }
        })*/
        val mapData = Transformations.distinctUntilChanged(originData)
        mapData.observe(this) {
            Log.d("=====", "onCreate: " + it)
        }
        originData.observe(this){
            Log.d("=====originData", "onCreate: " + it)
        }
        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            originData.value = "hello"
        }
    }
}