package com.aengussong.facedetector.screen.sessionList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aengussong.facedetector.R
import com.aengussong.facedetector.data.FaceRepository
import com.aengussong.facedetector.data.FaceRepositoryImpl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    private val repo: FaceRepository = FaceRepositoryImpl()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }

    override fun onStart() {
        super.onStart()
        val adapter = SessionListAdapter()
        sessions_rv.adapter = adapter
        disposable.add(repo.getPagedSessions().subscribe(adapter::submitList))
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}
