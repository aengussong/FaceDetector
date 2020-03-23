package com.aengussong.facedetector.screen.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aengussong.facedetector.R
import com.aengussong.facedetector.screen.camera.OpenCLCameraActivity
import com.aengussong.facedetector.screen.sessionList.ListActivity
import kotlinx.android.synthetic.main.activity_main.*

const val CAMERA_REQUEST_CODE = 1010

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_start.setOnClickListener { checkCameraPermissionAndOpenCameraActivity() }
        main_list.setOnClickListener { startActivity(Intent(this, ListActivity::class.java)) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCameraActivity()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkCameraPermissionAndOpenCameraActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        } else {
            openCameraActivity()
        }
    }

    private fun openCameraActivity() = startActivity(Intent(this, OpenCLCameraActivity::class.java))
}
