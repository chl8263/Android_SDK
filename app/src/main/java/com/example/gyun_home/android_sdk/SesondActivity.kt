package com.example.gyun_home.android_sdk

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.android.synthetic.main.activity_sesond.*
import kotlin.concurrent.thread

class SesondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesond)

        activity2_btn.setOnClickListener {
            var thread = Thread{

                run {
                    Thread.sleep(3000)
                    Handler(Looper.getMainLooper()).post {
                        activity2_textview.setText("쓰레드작동작동")
                    }

                }
            }
            thread.start()

        }
        activity2_btn_async.setOnClickListener {
            var async = object : AsyncTask<Int,Int,String>(){

                override fun doInBackground(vararg params: Int?): String {
                    var position = params[0]!!
                    while (position <100){
                        Thread.sleep(1000)
                        position = position+1

                        publishProgress(position)   //onProgressUpdate 에게 보고하는 부분
                    }
                    return "다운로드가 끝남"
                }

                override fun onProgressUpdate(vararg values: Int?) {
                    super.onProgressUpdate(*values)

                    activity2_textview.setText(values[0].toString()+"%")

                }

                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)

                    activity2_textview.setText(result)
                }
            }
            async.execute(20)

        }


    }
}
