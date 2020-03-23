package com.aengussong.facedetector.screen.result

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aengussong.facedetector.R
import kotlinx.android.synthetic.main.result_page.view.*

class FaceViewPagerAdapter(private val bitmaps:List<Bitmap>) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.result_page, parent, false))

    override fun getItemCount(): Int = bitmaps.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        result_image.setImageBitmap(bitmaps[position])
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)