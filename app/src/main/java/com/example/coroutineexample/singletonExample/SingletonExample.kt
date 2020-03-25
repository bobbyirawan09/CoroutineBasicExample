package com.example.coroutineexample.singletonExample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import com.example.coroutineexample.R

class SingletonExample : AppCompatActivity() {

    lateinit var viewModel: SingletonExampleViewModel

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singleton_example)

        viewModel = ViewModelProvider(this).get(SingletonExampleViewModel::class.java)
        viewModel.user.observe(this, Observer {
            println("debug: $it")
        })
        viewModel.setUserId("1")

        println("debug: ${SingletonObject.singletonUser.hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}
