package com.example.coroutineexample.singletonExample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.coroutineexample.singletonExample.model.User
import com.example.coroutineexample.singletonExample.repository.Repository

class SingletonExampleViewModel : ViewModel() {

    private val _userId: MutableLiveData<String> = MutableLiveData()

    val user: LiveData<User> = Transformations
        .switchMap(_userId) { userId ->
            Repository.getUserMethod(userId)
        }

    fun setUserId(userId: String) {
        val update = userId
        if (_userId.value == update) {
            return
        }
        _userId.value = userId
    }

    fun cancelJobs() {
        Repository.cancelJob()
    }
}