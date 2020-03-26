package com.example.coroutineexample.coroutineExample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutineexample.R
import kotlinx.android.synthetic.main.activity_easy_global_scope_example.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EasyGlobalScopeExample : AppCompatActivity() {

    private val TAG: String = "AppDebug"

    lateinit var parentJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_global_scope_example)

        main()

        button.setOnClickListener {
            parentJob.cancel()
        }
    }

    suspend fun work(i: Int) {
        delay(3000)
        println("Work $i done. ${Thread.currentThread().name}")
    }

    private fun main() {
        val startTime = System.currentTimeMillis()
        println("Start parent job...")
        parentJob = CoroutineScope(Main).launch {
            launch {
                work(1)
            }
            launch {
                work(2)
            }
            //The difference between this two below than two above is that global scope is more like
            //not following it's parent. so if the parentJob cancel, the global scope will still be running
            //It's not a good thing, since it not something we want. Imagine if you cancel a job and there are
            //some global scope launching, it won't cancelling those
            //GlobalScope.launch {
            //  work(1)
            //}
            //GlobalScope.launch {
            //  work(2)
            //}
        }
        parentJob.invokeOnCompletion {
            if (it != null) {
                println("Job was cancelled after ${System.currentTimeMillis() - startTime} ms")
            } else {
                println("Done in ${System.currentTimeMillis() - startTime} ms")
            }
        }
    }

    private fun println(message: String) {
        Log.d(TAG, message)
    }
}
