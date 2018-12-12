package com.adi.example

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adi.example.model.ResponseAlbum
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(val list: MutableList<ResponseAlbum>, private val onClick: onClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("bind", position.toString())
        holder.itemView.textView.text = list[position].title
        holder.itemView.setOnClickListener { onClick.onClickItem(list[position].id) }
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)

    interface onClickListener {
        fun onClickItem(id: Int)
    }
}