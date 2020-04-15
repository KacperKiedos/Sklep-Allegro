package com.example.allegrointernship.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.allegrointernship.models.Offer


@Database(
    entities = [
        Offer::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase :RoomDatabase(){

    abstract fun offersDao(): OffersDao
}