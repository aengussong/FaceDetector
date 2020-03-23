package com.aengussong.facedetector.screen.camera

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.aengussong.facedetector.data.FaceRepository

abstract class BaseCameraActivity<T:Any> : AppCompatActivity() {
    //todo move to presenter
    abstract val faceProcessor: BaseFaceProcessor<T>
    abstract val faceRepository: FaceRepository

    @get:LayoutRes
    abstract val contentViewRes: Int

    /**
     * Called after face was detected and stored. Takes session id for further result display
     * */
    abstract fun displayResult(sessionId:String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewRes)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onResume() {
        super.onResume()
        initLoader()
    }

    /**
     * Should be override in child class, if some kind of initialization is necessary. Will be called
     * in [onResume]
     * */
    open fun initLoader(){
    //empty implementation
    }

    /**
     * Main function to be called for data processing from camera
     *
     * @data - data from camera
     * */
    fun processData(data: T) {
        //todo move to presenter
        faceProcessor.processData(data) { wholeImage, croppedFaces ->
            val imageId = faceRepository.saveData(wholeImage, croppedFaces)
            displayResult(imageId)
        }
    }
}