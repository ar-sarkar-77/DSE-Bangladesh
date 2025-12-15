package com.anondo.dsebangladesh.data.onmodels

data class Stock_Data_Class (
    val id : Int,
    val name : String,
    val price : String,
    val change_price : String,
    val change_percent : String,
    val time : String,
    val status : Int
)