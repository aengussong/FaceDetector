package com.aengussong.facedetector.screen.result

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aengussong.facedetector.R
import kotlinx.android.synthetic.main.activity_result.*

const val PHOTO_ID_EXTRA = "photo_id_extra"

class ResultActivity : AppCompatActivity() {

    companion object{
        fun getIntent(context: Context, photoId:String): Intent{
            return Intent(context, ResultActivity::class.java).apply {
                this.putExtra(PHOTO_ID_EXTRA, photoId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        result_message.text = intent.getStringExtra(PHOTO_ID_EXTRA)
    }
}
