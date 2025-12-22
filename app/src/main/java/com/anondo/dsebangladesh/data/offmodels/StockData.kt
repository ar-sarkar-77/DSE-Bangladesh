package com.anondo.dsebangladesh.data.offmodels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class StockData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var price: String,
    var change_price: String,
    var change_percent: String,
    var time: String,
    var status: String
) : Parcelable