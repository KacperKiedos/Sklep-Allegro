package com.example.allegrointernship.retrofit

import com.example.allegrointernship.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val BASE_URL = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/"


    fun create() : RetrofitAPI
    {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(RetrofitAPI::class.java)
    }


}