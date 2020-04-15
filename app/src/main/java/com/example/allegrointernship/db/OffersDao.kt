package com.example.allegrointernship.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.allegrointernship.models.Offer

@Dao
interface OffersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffers(offers:List<Offer>)

    @Query("SELECT * FROM offers ORDER BY amount")
    fun getOffers() : LiveData<List<Offer>>
    
    @Query("DELETE FROM offers")
    fun clearOffers()
}
