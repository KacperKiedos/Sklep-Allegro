package com.example.allegrointernship.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.allegrointernship.db.OffersDao
import com.example.allegrointernship.models.Offer
import com.example.allegrointernship.models.Resource
import com.example.allegrointernship.models.OfferResponse
import com.example.allegrointernship.retrofit.RetrofitAPI
import com.example.allegrointernship.util.AppExecutors
import java.util.concurrent.TimeUnit


//class used for providing data from local db or API to the viewmodel
class OffersRepository(val retrofitAPI: RetrofitAPI, val offersDao: OffersDao,val appExecutors: AppExecutors) {

    //rateLimiter ArrayMap key for getOffers call
    private val OFFERS_FETCH_KEY = "OFFERS_SHOULD_FETCH"

    //we create RateLimiter in order to fetching data from network in specific moments, while app is running
    private val offersRateLimiter =
        RateLimiter<String>(
            3,
            TimeUnit.MINUTES
        )


    fun getOffers(forceRefresh:Boolean) : LiveData<Resource<List<Offer>>>{
        return object : NetworkBoundResource<List<Offer>, OfferResponse>(appExecutors) {
            override fun saveCallResult(item: OfferResponse) {
                //filtering the list before inserting to local db
                val offers = item.offers.filter { offer -> offer.price.amount in 50.0..1000.0}
                offersDao.insertOffers(offers)
            }

            override fun shouldFetch(data: List<Offer>?): Boolean {
                //conditions needed to satisfy before creating API call
                return data == null || data.isEmpty() || offersRateLimiter.shouldFetch(OFFERS_FETCH_KEY) || forceRefresh
            }

            override fun loadFromDb() = offersDao.getOffers()

            override fun createCall() = retrofitAPI.getOffers()

            override fun onFetchFailed() {
                //when API call has not succeeded we need to reset RateLimiter in order to perform call in next occasion
                offersRateLimiter.reset(OFFERS_FETCH_KEY)
            }
        }.asLiveData()
    }


}