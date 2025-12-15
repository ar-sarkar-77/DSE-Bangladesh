package com.anondo.dsebangladesh.views.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.anondo.dsebangladesh.data.offmodels.StockData
import com.anondo.dsebangladesh.data.onmodels.Stock_Data_Class
import com.anondo.dsebangladesh.databinding.ActivityMainBinding
import com.anondo.dsebangladesh.db.StockDao
import com.anondo.dsebangladesh.reducecode.ReduceCode
import com.anondo.dsebangladesh.views.adapter.StockAdapter
import org.json.JSONArray

class MainActivity : AppCompatActivity() , StockAdapter.handleUserClick {

    lateinit var binding : ActivityMainBinding
    var dataList : MutableList<Stock_Data_Class> = mutableListOf()
    var dataListss : MutableList<StockData> = mutableListOf()
    lateinit var dao: StockDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()

        binding.btnGainer.setOnClickListener {

            dataList.sortByDescending { it.change_price.toFloat() }
            adapter()

        }
        binding.btnLoser.setOnClickListener {

            dataList.sortBy { it.change_price.toFloat() }
            adapter()
        }
        binding.btnAll.setOnClickListener {

            dataList.clear()
            loadData()

        }
        binding.btnFav.setOnClickListener {

            dataList.clear()

        }

    }

    fun loadData(){

        /*binding.lottieAnimation.visibility = View.VISIBLE
        binding.recyclerStock.visibility = View.GONE*/

        dataList.clear()

    val queue = Volley.newRequestQueue(this)

    var jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET,
        "https://arsarkar.xyz/apps/get_dse_data.php",
        null,
        { responce: JSONArray ->

            binding.lottieAnimation.visibility = View.GONE
            binding.recyclerStock.visibility = View.VISIBLE

            for (i in 0..responce.length() - 1) {

                var jsonObject = responce.getJSONObject(i)

                val id = jsonObject.getInt("id")  // int
                val name = jsonObject.getString("name") // string
                val price = jsonObject.getDouble("price").toFloat() // float
                val change_price = jsonObject.getDouble("change_price").toFloat() // float
                val change_percent = jsonObject.getString("change_percent") // string
                val time = jsonObject.getString("time") // timestamp string
                val status = jsonObject.getInt("status") // status int

                dataList.add(
                    Stock_Data_Class(
                        id.toInt(),
                        name,
                        price.toString(),
                        change_price.toString(),
                        change_percent,
                        time,
                        status
                    )
                )

            }

            dataList.sortByDescending { it.status }
            adapter()

        }, {

            binding.lottieAnimation.visibility = View.VISIBLE
            binding.recyclerStock.visibility = View.GONE

            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
        })

    queue.add(jsonArrayRequest)

    }

    fun adapter(){

        binding.recyclerStock.layoutManager = LinearLayoutManager(this)
        binding.recyclerStock.adapter = StockAdapter( this , this, dataList )


    }

    override fun onFavClick() {
        loadData()
    }

}