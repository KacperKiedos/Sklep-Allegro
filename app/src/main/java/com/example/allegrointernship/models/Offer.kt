package com.example.allegrointernship.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "offers")
@Parcelize
data class Offer(
    @PrimaryKey val id:String,
    val name:String,
    val thumbnailUrl:String,
    @Embedded val price:Price,
    val description:String):Parcelable{

}