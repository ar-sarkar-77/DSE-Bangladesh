package com.anondo.dsebangladesh.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anondo.dsebangladesh.data.offmodels.StockData

@Database(entities = [StockData::class] , version = 1)
abstract class StockDatabase : RoomDatabase() {

    abstract fun addTaskDao() : StockDao

}