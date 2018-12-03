package com.dev.adi.ecosystem.controler

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Services {
    //your base url
    val URL = "https://api.bukalapak.com/"

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
    @GET("")
    fun get(
//        @Query("") xx: String,
//        @Query("") xx: Int
    ): Call<ResponseBody>

//
//    //2
//    @GET("")
//    fun getDetailProduct(
//        @Path("") xx: String
//    ): Call<ResponDetailProduct>
//
//    //2
//    @POST("")
//    fun replyFeed(
//        @Path("") xx : Int,
//        @Path("") xx : Int,
//        @Body content : JsonObject
//    ): Call<ResponseTweat>
}