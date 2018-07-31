package com.example.gyun_home.android_sdk

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyServiece : Service() {

    //서비스가 생성 될때
    override fun onCreate() {
        super.onCreate()
    }

    // 생성되기 전에 작동
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread {

            run {
                while (true) {
                    Thread.sleep(3000)
                    Log.e("service", "실행중")
                }
            }
        }.start()

        return START_STICKY //죽어도 다시 되살아남
    }

    //서비스가 뒤질때
    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onBind(intent: Intent?): IBinder {
        TODO()
    }


}