package com.example.gyun_home.android_sdk

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class ServiceBind : Service(){

    var message : String? =null

    override fun onCreate() {
        super.onCreate()

        message = "서비스가 작동중 입니다."
    }




    override fun onBind(intent: Intent?): IBinder {
        return MyBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class MyBinder : Binder(){

        fun getService() : ServiceBind {
            return this@ServiceBind
        }
    }
}