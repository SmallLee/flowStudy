package com.example.coroutinestudy

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.reflect.TypeToken

class MainActivity2 : AppCompatActivity() {


    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        lifecycle.addObserver(mainViewModel)
        ViewModelProvider(this).get(MainViewModel::class.java)
        findViewById<Button>(R.id.btn).setOnClickListener {
            val t = object : APIType<List<String>>() {}
            gettype(t)
        }
    }

    fun <T> gettype(type: APIType<T>) {
        val tClass = ClassUtil.getTClass(type)
        Log.d("======", ": $tClass")
    }
}