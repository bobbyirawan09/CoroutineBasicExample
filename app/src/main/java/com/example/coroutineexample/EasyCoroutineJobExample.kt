package com.example.coroutineexample

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_easy_coroutine_job_example.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class EasyCoroutineJobExample : AppCompatActivity() {

    private val PROGRESS_MAX = 100
    private val PROGRESS_START = 0
    private val JOB_TIME = 4000
    private lateinit var job: CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_coroutine_job_example)

        job_button.setOnClickListener {
            //this logic is used to check if a job has been init before or not, if not it will initiate
            if (!::job.isInitialized) {
                initJob()
            }
            job_progress_bar.startJobOrCancel(job)
        }
    }

    fun ProgressBar.startJobOrCancel(job: Job) {
        if (this.progress > 0) {
            println("$job is already active. Cancelling....")
            resetJob()
        } else {
            job_button.text = "Cancel Job #1"
            //if we not pass the 'job' in the coroutine context, let say we want to cancel a job, it will cancel all job in that context
            //so it will cancel all job in IO. But if we add 'job' in the corouting context and cancel that 'job' it only cancel that 'job'
            //not the whole IO scope , it's quite important. You don't want to cancel a whole IO, there could be some crash or strange behavior in your app
            //in simplest way, it make your job isolated from the other
            CoroutineScope(IO + job).launch {
                println("coroutine ${this} is activated with job $job")

                for (i in PROGRESS_START..PROGRESS_MAX) {
                    delay((JOB_TIME / PROGRESS_MAX).toLong())
                    this@startJobOrCancel.progress = i
                }
                updatesJobCompleteTextField("Job is complete")
            }
        }
    }

    private fun updatesJobCompleteTextField(text: String) {
        GlobalScope.launch(Main) {
            job_complete_text.text = text
        }
    }

    private fun resetJob() {
        if (job.isActive || job.isCompleted) {
            job.cancel(CancellationException("Resetting job"))
        }
        initJob()
    }

    fun initJob() {
        job_button.text = "Start Job #1"
        updatesJobCompleteTextField("")
        //why always create new job? because when we cancelled a job, we need a new one
        job = Job()
        //when calling job cancel, invoke completion will automatically called
        job.invokeOnCompletion {
            it?.message.let {
                var msg = it
                if (msg.isNullOrBlank()) {
                    msg = "Unknown cancellation Error"
                }
                println("$job was cancelled. Reason : $msg")
                showToast(msg)
            }
        }
        job_progress_bar.max = PROGRESS_MAX
        job_progress_bar.progress = PROGRESS_START
    }

    fun showToast(text: String) {
        //using this with annotation is marking that no matter what, it will run in that context
        GlobalScope.launch(Main) {
            Toast.makeText(this@EasyCoroutineJobExample, text, Toast.LENGTH_LONG).show()
        }
    }
}
