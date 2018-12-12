package com.adi.example

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


abstract class EndlessOnScrollListener : RecyclerView.OnScrollListener() {
    private var mPreviousTotal = 0
    private var mLoading = true
    private var currentPage = 0

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = recyclerView.layoutManager.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
                currentPage++
            }
        }
        val visibleThreshold = 5
        if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            onLoadMore(currentPage)
            mLoading = true
        }
    }

    fun resetScroll() {
        currentPage = 0
        mLoading = true
        mPreviousTotal = 0
    }

    abstract fun onLoadMore(page : Int)

}