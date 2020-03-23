package com.aengussong.facedetector.screen.camera

import android.widget.Toast
import com.aengussong.facedetector.data.FaceRepository
import com.aengussong.facedetector.R
import com.aengussong.facedetector.screen.result.ResultActivity
import com.aengussong.facedetector.data.FaceRepositoryImpl
import kotlinx.android.synthetic.main.activity_camera.*
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class OpenCLCameraActivity : BaseCameraActivity<Mat>(), CameraBridgeViewBase.CvCameraViewListener {

     override val faceProcessor:BaseFaceProcessor<Mat> = OpenCLFaceProcessor()

    //todo add loader for user friendly screen switch?
    override val contentViewRes: Int
        get() = R.layout.activity_camera

    override val faceRepository: FaceRepository
        get() = FaceRepositoryImpl()

    //todo ???
    private var grayscaleImage: Mat? = null
    private var absoluteFaceSize = 0.0

    private val openCvLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> initializeOpenCvDependencies()
                else -> {
                    displayCvLoaderError(status)
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun initLoader() {
        //todo should destroy something on activity stop?
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, openCvLoaderCallback)
    }

    override fun displayResult(sessionId: String) {
        startActivity(ResultActivity.getIntent(this, sessionId))
    }

    private fun displayCvLoaderError(status: Int) {
        val message = when (status) {
            LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION -> R.string.err_ocvl_manager
            LoaderCallbackInterface.INIT_FAILED -> R.string.err_ocvl_failed
            LoaderCallbackInterface.INSTALL_CANCELED -> R.string.err_ocvl_install
            LoaderCallbackInterface.MARKET_ERROR -> R.string.err_ocvl_market
            else -> R.string.err_generic
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun initializeOpenCvDependencies() {
        (faceProcessor as OpenCLFaceProcessor).initOpenCvDependencies(this)
        openCvCamera.setCvCameraViewListener(this)
        openCvCamera.enableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        grayscaleImage = Mat(height, width, CvType.CV_8UC4)
        // The faces will be a 20% of the height of the screen
        absoluteFaceSize = height * 0.2
        (faceProcessor as OpenCLFaceProcessor).initOpenCvVariables(grayscaleImage, absoluteFaceSize)
    }

    override fun onCameraViewStopped() {}

    override fun onCameraFrame(aInputFrame: Mat?): Mat? {
        Imgproc.cvtColor(aInputFrame, grayscaleImage, Imgproc.COLOR_RGBA2RGB)
        //todo add some kind of throttling for camera frames
        aInputFrame?.let { processData(it) }
        return aInputFrame
    }
}