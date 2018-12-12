package com.adi.example.model

data class ResponseAlbumPhotos(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)