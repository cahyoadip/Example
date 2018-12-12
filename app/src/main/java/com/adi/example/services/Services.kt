package com.dev.adi.ecosystem.controler

import com.adi.example.model.ResponseAlbum
import com.adi.example.model.ResponseAlbumPhotos
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class Services {
    //your base url
    val URL = "https://jsonplaceholder.typicode.com/"

    fun create(): ServicesInterface {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(URL)
                .build()
        return retrofit.create(ServicesInterface::class.java)
    }
}

interface ServicesInterface {

    //1
    @GET("/albums")
    fun getAlbum(): Call<ArrayList<ResponseAlbum>>


    //1
    @GET("/albums/{id}/photos")
    fun getAlbumPhotos(
        @Path("id") id: Int
    ): Call<ArrayList<ResponseAlbumPhotos>>

}