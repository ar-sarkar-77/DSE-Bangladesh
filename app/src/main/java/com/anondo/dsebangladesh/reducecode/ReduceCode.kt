package com.anondo.dsebangladesh.reducecode

import android.content.Context
import androidx.room.Room
import com.anondo.dsebangladesh.db.StockDao
import com.anondo.dsebangladesh.db.StockDatabase

object ReduceCode {

    fun database(context: Context): StockDao {

        var db = Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "Stock_Data"
        ).allowMainThreadQueries().build()

        var dao = db.addTaskDao()

        return dao
    }

}