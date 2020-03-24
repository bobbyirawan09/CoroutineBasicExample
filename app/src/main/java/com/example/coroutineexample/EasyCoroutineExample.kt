package com.example.coroutineexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_easy_coroutine_example.*
import kotlinx.coroutines.*

class EasyCoroutineExample : AppCompatActivity() {

    private val RESULT_1 = "Result #1"
    private val RESULT_2 = "Result #2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_coroutine_example)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                fakeApiRequest()
            }
        }

        button_next_example.setOnClickListener {
            startActivity(Intent(this, EasyCoroutineJobExample::class.java))
        }
    }

    private fun setNewText(input: String) {
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Dispatchers.Main) {
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest() {
        val result1 = getResultFromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)

        val result2 = getResultFromApi2()
        setTextOnMainThread(result2)
    }

    private suspend fun getResultFromApi(): String {
        logThread("getResultFromApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResultFromApi2(): String {
        logThread("getResultFromApi2")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}
