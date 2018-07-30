package com.example.gyun_home.android_sdk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_activirt)

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout1,MyFragment()).commit()
    }
}
