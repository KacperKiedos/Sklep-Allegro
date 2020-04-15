package com.example.allegrointernship.ui.offers

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.room.Room
import com.example.allegrointernship.db.AppDatabase
import com.example.allegrointernship.models.Offer
import com.example.allegrointernship.models.Resource
import com.example.allegrointernship.repository.OffersRepository
import com.example.allegrointernship.retrofit.RetrofitClient
import com.example.allegrointernship.util.AppExecutors

class OffersViewModel(application: Application) : AndroidViewModel(application){

    //we will use this application context in database object constructor
    private val applicationContext: Context = getApplication<Application>().applicationContext

    //retrofit object
    private val retrofit = RetrofitClient.create()

    //database and DAO objects
    private val database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "appDB").build()
    private val offersDao = database.offersDao()

    //object, which will help to manage threads in repository
    private val appExecutors = AppExecutors()

    private val repository = OffersRepository(retrofit,offersDao,appExecutors)


    //MutableLiveData used for triggering Transformations.switchmap function
    private var _trigger: MutableLiveData<Boolean> = MutableLiveData()

    //LiveData which is used for signaling repository if forceRefresh is needed
    private var _forceRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    val forceRefresh:LiveData<Boolean>
        get() = _forceRefresh


    //at the beginning we need to get offers list from repository
    init{
        forceRefresh()
    }

    //LiveData which gets data from repository, every time after trigger LiveData has changed value
    val offers:LiveData<Resource<List<Offer>>> = Transformations.switchMap(_trigger) {
        repository.getOffers(forceRefresh.value!!)
    }

    fun forceRefresh()
    {
       _forceRefresh.value = true
        _trigger.value = _trigger.value
        _forceRefresh.value = false
    }
}