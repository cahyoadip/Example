package com.adi.example

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log.e
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.adi.example.model.ResponseAlbum
import com.adi.example.model.ResponseAlbumPhotos
import com.dev.adi.ecosystem.controler.Services
import kotlinx.android.synthetic.main.activity_album_photos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumPhotosActivity : AppCompatActivity(), AlbumPhotoAdapter.onClickListener, AlbumPhotoAdapter.onClickUrlListener {
    override fun onClickUrl(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onClickItem(id: Int) {
        e("click photo", id.toString())
    }

    lateinit var adapter: AlbumPhotoAdapter
    var dataAlbumPhoto : ArrayList<ResponseAlbumPhotos> = arrayListOf()
    var tempAlbumPhoto : ArrayList<ResponseAlbumPhotos> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_photos)
        title = "Photo of Album"

        adapter = AlbumPhotoAdapter(tempAlbumPhoto, this, this, this)
        rv_photo.layoutManager = LinearLayoutManager(this)
        rv_photo.itemAnimator = DefaultItemAnimator()
        rv_photo.adapter = adapter

        val extra = intent.extras
        val id = extra.getString("id")
        e("id", id)

        getAlbumPhotos(id.toInt())
        avi.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.dashboard, menu)
        val mSearch = menu.findItem(R.id.action_search)
        val mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = "Search"
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchBody(newText.toLowerCase())
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun searchBody (q : String) {
        val result = dataAlbumPhoto.filter { it.title.contains(q.toLowerCase())}
        e("result", result.toString())
        if (result.isEmpty()) {
            Toast.makeText(baseContext, "Data Not Found", Toast.LENGTH_SHORT).show()
        } else {
            tempAlbumPhoto.clear()
            tempAlbumPhoto.addAll(result)
            adapter.notifyDataSetChanged()
        }
    }

    private fun getAlbumPhotos(id : Int) {
        val service = Services().create()
        service.getAlbumPhotos(id).enqueue(object : Callback<ArrayList<ResponseAlbumPhotos>> {
            override fun onResponse(call: Call<ArrayList<ResponseAlbumPhotos>>, response: Response<ArrayList<ResponseAlbumPhotos>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        tempAlbumPhoto.addAll(body)
                        dataAlbumPhoto.addAll(tempAlbumPhoto)
                        adapter.notifyDataSetChanged()
                        avi.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<ResponseAlbumPhotos>>, t: Throwable) {
                Toast.makeText(baseContext, "Server error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
