package com.anondo.dsebangladesh.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.anondo.dsebangladesh.data.offmodels.StockData

@Dao
interface StockDao {

    @Insert
    fun Add_Stock(stock : StockData)

    @Update
    fun Edit_Stock(stock : StockData)

    @Query("SELECT * FROM StockData")
    fun Get_All_Stock(): MutableList<StockData>

    @Delete
    fun Delete_Stock(stock : StockData)
}