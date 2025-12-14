package com.anondo.dsebangladesh.views.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.anondo.dsebangladesh.data.onmodels.Stock_Data_Class
import com.anondo.dsebangladesh.databinding.ActivityMainBinding
import com.anondo.dsebangladesh.views.adapter.StockAdapter
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    var dataList : MutableList<Stock_Data_Class> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LoadData()

        binding.btnGainer.setOnClickListener {

            dataList.sortByDescending { it.change_price.toFloat() }
            LoadData()

        }
        binding.btnLoser.setOnClickListener {

            dataList.sortBy { it.change_price.toFloat() }
            LoadData()

        }
        binding.btnAll.setOnClickListener {

            dataList.clear()
            LoadData()

        }

    }

    fun LoadData(){

    val queue = Volley.newRequestQueue(this)

    var jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET,
        "https://arsarkar.xyz/apps/get_dse_data.php",
        null,
        { responce: JSONArray ->

            for (i in 0..responce.length() - 1) {

                var jsonObject = responce.getJSONObject(i)

                val id = jsonObject.getInt("id")  // int
                val name = jsonObject.getString("name") // string
                val price = jsonObject.getDouble("price").toFloat() // float
                val change_price = jsonObject.getDouble("change_price").toFloat() // float
                val change_percent = jsonObject.getString("change_percent") // string
                val time = jsonObject.getString("time") // timestamp string

                dataList.add(
                    Stock_Data_Class(
                        id.toInt(),
                        name,
                        price.toString(),
                        change_price.toString(),
                        change_percent,
                        time
                    )
                )

            }

            binding.recyclerStock.layoutManager = LinearLayoutManager(this)
            binding.recyclerStock.adapter = StockAdapter(this, dataList.toMutableList())


        },
        {
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
        })

    queue.add(jsonArrayRequest)

}

}