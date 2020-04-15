package com.example.allegrointernship.retrofit

import androidx.lifecycle.LiveData
import com.example.allegrointernship.models.OfferResponse
import retrofit2.http.GET

interface RetrofitAPI {

    @GET("offers")
    fun getOffers() : LiveData<ApiResponse<OfferResponse>>
}