package com.example.coroutineexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutineexample.coroutineExample.EasyCoroutineAsyncExample
import com.example.coroutineexample.coroutineExample.EasyCoroutineExample
import com.example.coroutineexample.coroutineExample.EasyCoroutineJobExample
import com.example.coroutineexample.singletonExample.SingletonExample
import kotlinx.android.synthetic.main.activity_start_page.*

class StartPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)

        button_coroutine_basic_example.setOnClickListener {
            startActivity(Intent(this, EasyCoroutineExample::class.java))
        }

        button_coroutine_basic_job_example.setOnClickListener {
            startActivity(Intent(this, EasyCoroutineJobExample::class.java))
        }

        button_coroutine_async_example.setOnClickListener {
            startActivity(Intent(this, EasyCoroutineAsyncExample::class.java))
        }

        button_singleton_example.setOnClickListener {
            startActivity(Intent(this, SingletonExample::class.java))
        }
    }
}
