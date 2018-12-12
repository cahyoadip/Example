package com.adi.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumActivity : AppCompatActivity(), AlbumAdapter.onClickListener {
    override fun onClickItem(id: Int) {
        val intent = Intent(baseContext, AlbumPhotosActivity::class.java)
        intent.putExtra("id", id.toString())
        startActivity(intent)
    }

    lateinit var adapter: AlbumAdapter
    var dataAlbum : ArrayList<ResponseAlbum> = arrayListOf()
    var tempAlbum : ArrayList<ResponseAlbum> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Album"

        adapter = AlbumAdapter(tempAlbum, this)
        rv_album.layoutManager = LinearLayoutManager(this)
        rv_album.itemAnimator = DefaultItemAnimator()
        rv_album.adapter = adapter
//        rv_album.addOnScrollListener(scrollData())

        getAlbum()
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
        val result = dataAlbum.filter { it.title.contains(q.toLowerCase())}
        e("result", result.toString())
        if (result.isEmpty()) {
            Toast.makeText(baseContext, "Data Not Found", Toast.LENGTH_SHORT).show()
        } else {
            tempAlbum.clear()
            tempAlbum.addAll(result)
            adapter.notifyDataSetChanged()
        }
    }

    private fun scrollData(): EndlessOnScrollListener {
        return object : EndlessOnScrollListener() {
            override fun onLoadMore(page: Int) {
                loadMore(page)
            }
        }
    }

    fun loadMore (page : Int) {
        if ((page * 10) + 10 <= dataAlbum.size) {
            dataAlbum.addAll(dataAlbum.subList(page * 10, (page * 10) + 10))
        } else if (dataAlbum.size > (page * 10)) {
            dataAlbum.addAll(dataAlbum.subList(page * 10, dataAlbum.size))
        }
    }

    private fun getAlbum() {
        val service = Services().create()
        service.getAlbum().enqueue(object : Callback<ArrayList<ResponseAlbum>> {
            override fun onResponse(call: Call<ArrayList<ResponseAlbum>>, response: Response<ArrayList<ResponseAlbum>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        dataAlbum.addAll(body)
                        tempAlbum.addAll(dataAlbum)
                        adapter.notifyDataSetChanged()
                        avi.visibility = View.GONE
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<ResponseAlbum>>, t: Throwable) {
                Toast.makeText(baseContext, "Server error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
