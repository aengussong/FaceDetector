package com.aengussong.facedetector.screen.sessionList


import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import com.aengussong.facedetector.data.SessionEntity


class SessionListAdapter : PagedListAdapter<SessionEntity, SessionViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder =
        SessionViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SessionEntity>() {
            override fun areItemsTheSame(oldItem: SessionEntity, newItem: SessionEntity): Boolean =
                oldItem.timestamp == newItem.timestamp

            override fun areContentsTheSame(oldItem: SessionEntity, newItem: SessionEntity): Boolean =
                oldItem == newItem
        }
    }
}