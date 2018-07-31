package com.example.gyun_home.android_sdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MybroadCastReceiver : BroadcastReceiver(){


    override fun onReceive(context: Context?, intent: Intent?) {


        when(intent!!.action){
            Intent.ACTION_CAMERA_BUTTON -> Toast.makeText(context,"카메가 버튼 눌림",Toast.LENGTH_SHORT).show()
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context,"베터리가 없음",Toast.LENGTH_SHORT).show()
        }
    }


}