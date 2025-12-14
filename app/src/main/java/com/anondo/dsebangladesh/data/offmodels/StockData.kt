package com.anondo.dsebangladesh.data.offmodels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class StockData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: String,
    val change_price: String,
    val change_percent: String,
    val time: String,
    val status: String
) : Parcelable