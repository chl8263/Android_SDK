package com.example.gyun_home.android_sdk

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.graphics.PixelFormat
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ServiceConnection ,LocationListener{


    var isservice = false

    var myBroadCastReiver = MybroadCastReceiver()
    var intentFilter = IntentFilter()

    var locationMnager : LocationManager? =null

    var overlayView : View? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService()
        service()
        newActivity()
        fragment()
        toastAndSnackbar()
        shareadPreference()
        permission()
        broadCastReceiver()
        locationManager()
        winddwManager()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun winddwManager() {

        if(Settings.canDrawOverlays(this)) {


            overlayView = layoutInflater.inflate(R.layout.overlay, null)

            var layout_flag: Int

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layout_flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                layout_flag = WindowManager.LayoutParams.TYPE_PHONE
            }

            var params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    layout_flag,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            )
            windowManager.addView(overlayView, params)
            overlayView!!.setOnClickListener { windowManager
                    .removeView(overlayView)
            }
           /* var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)*/
        }else {
            var intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("pakage:" + packageName))
            startActivityForResult(intent, 0)
        }
    }

    override fun onLocationChanged(location: Location?) {
        //위치정보
        Log.e("location", location!!.latitude.toString());
        Log.e("location", location!!.longitude.toString());
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //연결 상태가 변결 됐을때
    }

    override fun onProviderEnabled(provider: String?) {
        //연결 됐을때
    }

    override fun onProviderDisabled(provider: String?) {
        //연결 끊겼을때
    }
    @SuppressLint("MissingPermission")
    fun locationManager(){

        locationMnager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(locationMnager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationMnager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100f, this)
        }else {
            locationMnager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100f, this)
        }
    }
    fun broadCastReceiver(){
        btn_camera.setOnClickListener {
            var Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
            sendBroadcast(Intent(android.content.Intent.ACTION_CAMERA_BUTTON))
        }
    }

    override fun onResume() {
        super.onResume()

        //배터리부족
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW)
        intentFilter.addAction(Intent.ACTION_CAMERA_BUTTON)

        registerReceiver(myBroadCastReiver,intentFilter)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isservice = false
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        var myBinder = service as ServiceBind.MyBinder
        var service = myBinder.getService()

        Log.e("bindservice", service.message)

        isservice = true
    }





    fun bindService() {
        var bindservice = Intent(this, MyServiece::class.java)
        bindService(bindservice, this, Context.BIND_AUTO_CREATE) //3번째 파라미터는 서비스가 생성 안된경우 생성한다는 의미

        service_state.setOnClickListener {
            if (isservice == true) {
                Toast.makeText(this, "서비스가 연결되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "서비스가 연결 해지 되었습니다", Toast.LENGTH_SHORT).show()
            }
        }
        service_detach.setOnClickListener {
            if (isservice) {
                unbindService(this)
                isservice = false   // 서비스가 unbind 되는 경우는 os 가 메모리가 부족하다고 느낄때 직접적으로 하기때문에 완벽한 서비스 해지가 아니다 그래서 플래그 값을 넣어 줘야한다
            }
        }
    }

    fun service() {
        var service = Intent(this, MyServiece::class.java)
        startService(service)
    }

    fun newActivity() {
        button.setOnClickListener {

            var secondActivity = Intent(this, SesondActivity::class.java)
            startActivity(secondActivity)
        }
    }

    fun fragment() {
        fragment.setOnClickListener {

            var intnet = Intent(this, FragmentActivity::class.java)
            startActivity(intnet)
        }
    }

    fun toastAndSnackbar() {
        toast_btn.setOnClickListener {

            Toast.makeText(this, "토스트메세지", Toast.LENGTH_LONG).show()

        }
        snakbar_btn.setOnClickListener {

            Snackbar.make(mainView, "스넥바 메세지", Snackbar.LENGTH_LONG).show()
        }
    }

    fun shareadPreference() {

        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var edit = sharedPreferences.edit()

        var save_string = sharedPreferences.getString("userId", "아이디 입력해 주세요")
        editText.setText(save_string)

        btn_save.setOnClickListener {

            edit.putString("userId", editText.text.toString())
        }
    }

    fun permission() {

        var calendar = Manifest.permission.READ_CALENDAR
        var camera = Manifest.permission.CAMERA
        var contacts = Manifest.permission.READ_CONTACTS

        var location = Manifest.permission.ACCESS_FINE_LOCATION
        var microphone = Manifest.permission.RECORD_AUDIO
        var phone = Manifest.permission.READ_PHONE_NUMBERS

        var sensor = Manifest.permission.BODY_SENSORS
        var sms = Manifest.permission.SEND_SMS
        var storage = Manifest.permission.READ_EXTERNAL_STORAGE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(calendar, camera, contacts, location, microphone, phone, sensor, sms, sensor), 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0) {
            for (i in permissions.indices) {

                Log.e("권한: ", permissions[i] + " 허가상태 " + grantResults[i])
            }
        }
    }
}
