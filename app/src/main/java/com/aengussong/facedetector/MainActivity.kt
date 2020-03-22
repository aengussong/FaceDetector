package com.aengussong.facedetector

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_start.setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
        main_list.setOnClickListener { startActivity(Intent(this, ListActivity::class.java)) }
    }
}
