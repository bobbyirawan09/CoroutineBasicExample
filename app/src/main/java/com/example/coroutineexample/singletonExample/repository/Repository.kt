package com.example.coroutineexample.singletonExample.repository

import androidx.lifecycle.LiveData
import com.example.coroutineexample.singletonExample.api.RetrofitBuilder
import com.example.coroutineexample.singletonExample.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    var job: CompletableJob? = null

    fun getUserMethod(userId: String): LiveData<User> {
        job = Job()
        return object: LiveData<User>() {
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(IO + it).launch {
                        val user = RetrofitBuilder.apiService.getUserId(userId)
                        withContext(Main){
                            value = user
                            it.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJob() {
        job?.cancel()
    }
}