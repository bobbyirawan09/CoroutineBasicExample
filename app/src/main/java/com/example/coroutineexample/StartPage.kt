package com.example.coroutineexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}
