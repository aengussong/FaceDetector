package com.aengussong.facedetector.screen.result

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aengussong.facedetector.R
import com.aengussong.facedetector.data.FaceRepository
import com.aengussong.facedetector.data.FaceRepositoryImpl
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_result.*

const val PHOTO_ID_EXTRA = "photo_id_extra"

class ResultActivity : AppCompatActivity() {

    private val repository: FaceRepository = FaceRepositoryImpl()
    private val disposable = CompositeDisposable()

    companion object {
        fun getIntent(context: Context, photoId: String): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                this.putExtra(PHOTO_ID_EXTRA, photoId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        disposable.add(repository.getFaces(intent.getStringExtra(PHOTO_ID_EXTRA))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                displayData(list)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private fun displayData(faces: List<Bitmap>) {
        result_pager.adapter = FaceViewPagerAdapter(faces)
        TabLayoutMediator(result_tab, result_pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.result_tab_whole_image)
                else -> getString(R.string.result_tab_face, position)
            }
        }.attach()
    }
}
