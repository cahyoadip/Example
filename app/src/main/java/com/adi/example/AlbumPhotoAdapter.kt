package com.adi.example

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adi.example.model.ResponseAlbumPhotos
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_album_photos.view.*

class AlbumPhotoAdapter(val list: MutableList<ResponseAlbumPhotos>, private val context: Context, private val onClick: onClickListener, private val onUrl: onClickUrlListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album_photos, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(context).load(list[position].thumbnailUrl).into(holder.itemView.imageView)
        holder.itemView.textView.text = list[position].title
        holder.itemView.textView2.text = list[position].url
        holder.itemView.textView2.setOnClickListener { onUrl.onClickUrl(list[position].url) }
        holder.itemView.setOnClickListener { onClick.onClickItem(list[position].id) }
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)

    interface onClickListener {
        fun onClickItem(id: Int)
    }

    interface onClickUrlListener {
        fun onClickUrl(url: String)
    }
}