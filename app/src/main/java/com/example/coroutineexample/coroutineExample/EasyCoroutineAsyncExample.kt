package com.example.coroutineexample.coroutineExample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutineexample.R
import kotlinx.android.synthetic.main.activity_easy_coroutine_async_example.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.system.measureTimeMillis

class EasyCoroutineAsyncExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_coroutine_async_example)

        button.setOnClickListener {
            setNewText("Clicked!")

            fakeApiRequestAsync()
            fakeApiRequestLaunch()
        }
    }

    private fun fakeApiRequestLaunch() {
        //Method below is using the usual way (launch)
        //Not effective coz it seems like isolated one another and the code repeatedly created just to show the result
        val startTime = System.currentTimeMillis()
        val parentJob = CoroutineScope(IO).launch {
            val job1 = launch {
                val time1 = measureTimeMillis {
                    println("debug: launching job1 in thread: ${Thread.currentThread().name}")
                    val result = getResult1FromApi()
                    setTextOnMainThread("Got $result")
                }
                println("Completed job 1 in $time1 ms")
            }
            //The .join() means that the coroutine needs to wait job1 to finish then continue to other job in this coroutine scope
            //The work will be sequential, not parallel
            //job1.join()

            val job2 = launch {
                val time1 = measureTimeMillis {
                    println("debug: launching job1 in thread: ${Thread.currentThread().name}")
                    val result = getResult2FromApi()
                    setTextOnMainThread("Got $result")
                }
                println("Completed job 2 in $time1 ms")
            }
        }
        parentJob.invokeOnCompletion {
            //will be shown the longest of a job to finish (job 2 time)
            println("debug: total elapsed time: ${System.currentTimeMillis() - startTime}")
        }
    }

    private fun fakeApiRequestAsync() {
        //Below is the async way
        //When to use async? When we want to get the result and access it from outside of the coroutine
        CoroutineScope(IO).launch {
            val executionTime = measureTimeMillis {
                val result1: Deferred<String> = async {
                    println("debug: launching job1 in ${Thread.currentThread().name} thread ")
                    getResult1FromApi()
                }
                val result2: Deferred<String> = async {
                    println("debug: launching job2 in ${Thread.currentThread().name} thread ")
                    getResult2FromApi()
                }

                //Below is one way if you want to access the result outside of the coroutine scope
                //Not clean and it's quite painful if do it in a large scale app
                var result = ""
                val job = launch {
                    result = getResult1FromApi()
                }
                job.join() //need this because if not, it will show blank. Because the job is being run in a coroutine, while you want strike show the result
                println(result)

                setTextOnMainThread("Got result1 -> ${result1.await()}")
                setTextOnMainThread("Got result2 -> ${result2.await()}")
            }
            println("debug: total time elapsed: $executionTime")
        }
    }

    private fun setNewText(input: String) {
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setNewText(input)
        }
    }

    private suspend fun getResult1FromApi(): String {
        delay(1000)
        return "Result #1"
    }

    private suspend fun getResult2FromApi(): String {
        delay(1700)
        return "Result #2"
    }
}
