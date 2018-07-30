package com.example.gyun_home.android_sdk

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            var secondActivity = Intent(this,SesondActivity::class.java)
            startActivity(secondActivity)
        }
        fragment.setOnClickListener {

            var intnet = Intent(this,FragmentActivity::class.java)
            startActivity(intnet)
        }

        toast_btn.setOnClickListener {

            Toast.makeText(this,"토스트메세지",Toast.LENGTH_LONG).show()

        }
        snakbar_btn.setOnClickListener {

            Snackbar.make(mainView,"스넥바 메세지",Snackbar.LENGTH_LONG).show()
        }

        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var edit = sharedPreferences.edit()

        var save_string = sharedPreferences.getString("userId","아이디 입력해 주세요")
        editText.setText(save_string)

        btn_save.setOnClickListener {

            edit.putString("userId",editText.text.toString())
        }


        var calendar = Manifest.permission.READ_CALENDAR
        var camera = Manifest.permission.CAMERA
        var contacts =  Manifest.permission.READ_CONTACTS

        var location = Manifest.permission.ACCESS_FINE_LOCATION
        var microphone =Manifest.permission.RECORD_AUDIO
        var phone = Manifest.permission.READ_PHONE_NUMBERS

        var sensor = Manifest.permission.BODY_SENSORS
        var sms = Manifest.permission.SEND_SMS
        var storage = Manifest.permission.READ_EXTERNAL_STORAGE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(calendar,camera,contacts,location,microphone,phone,sensor,sms,sensor),0)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 0){
            for(i in permissions.indices){

                Log.e("권한: ", permissions[i]+" 허가상태 "+grantResults[i])
            }
        }
    }
}
