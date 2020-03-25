package com.example.coroutineexample.singletonExample

import com.example.coroutineexample.singletonExample.model.User

object SingletonObject {

    //This lazy mean that the object only created only one time
    val singletonUser by lazy {
        User("irawanbobby09@gmail.com", "bobby", "profile_picture.png")
    }
}