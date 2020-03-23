package com.aengussong.facedetector.screen.sessionList


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.aengussong.facedetector.R
import com.aengussong.facedetector.data.SessionEntity

class SessionViewHolder(parent :ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.session_text)
    var session : SessionEntity? = null

    fun bindTo(session : SessionEntity?) {
        this.session = session
        nameView.text = session?.timestamp
    }
}