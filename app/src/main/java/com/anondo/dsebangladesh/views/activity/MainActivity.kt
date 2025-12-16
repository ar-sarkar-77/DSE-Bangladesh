package com.anondo.dsebangladesh.views.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.anondo.dsebangladesh.R
import com.anondo.dsebangladesh.data.onmodels.Stock_Data_Class
import com.anondo.dsebangladesh.databinding.ActivityMainBinding
import com.anondo.dsebangladesh.db.StockDao
import com.anondo.dsebangladesh.views.adapter.StockAdapter
import org.json.JSONArray

class MainActivity : AppCompatActivity(), StockAdapter.handleUserClick {

    lateinit var binding: ActivityMainBinding
    var dataList: MutableList<Stock_Data_Class> = mutableListOf()
    var dataListss: MutableList<Stock_Data_Class> = mutableListOf()
    lateinit var dao: StockDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()

        binding.btnGainer.setOnClickListener {

            loadingAnim("no")

            dataList.sortByDescending { it.change_price.toFloat() }
            adapter(dataList)

        }
        binding.btnLoser.setOnClickListener {

            loadingAnim("no")

            dataList.sortBy { it.change_price.toFloat() }
            adapter(dataList)

        }
        binding.btnAll.setOnClickListener {

            dataList.clear()
            loadData()

        }
        binding.btnFav.setOnClickListener {

            search()

        }

    }

    fun loadData() {

        dataList.clear()

        val queue = Volley.newRequestQueue(this)

        var jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            "https://arsarkar.xyz/apps/get_dse_data.php",
            null,
            { responce: JSONArray ->

                loadingAnim("no")

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
                            id,
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
                adapter(dataList)

            }, {

                loadingAnim("yes")

                Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonArrayRequest)

    }

    fun adapter(datalistes: MutableList<Stock_Data_Class>) {

        binding.recyclerStock.layoutManager = LinearLayoutManager(this)
        binding.recyclerStock.adapter = StockAdapter(this, this, datalistes)


    }

    fun search() {

        val queue = Volley.newRequestQueue(this@MainActivity)

        dataListss.clear()

        var jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            "https://arsarkar.xyz/apps/search.php?status=1",
            null,
            { responce: JSONArray ->

                //  Empty response
                if (responce.length() == 0) {
                    noDataAnim()
                    return@JsonArrayRequest
                }

                loadingAnim("no")

                for (i in 0..responce.length() - 1) {

                    var jsonObject = responce.getJSONObject(i)

                    val id = jsonObject.getInt("id")  // int
                    val name = jsonObject.getString("name") // string
                    val price = jsonObject.getDouble("price").toFloat() // float
                    val change_price = jsonObject.getDouble("change_price").toFloat() // float
                    val change_percent = jsonObject.getString("change_percent") // string
                    val time = jsonObject.getString("time") // timestamp string
                    val status = jsonObject.getInt("status") // status int

                    dataListss.add(
                        Stock_Data_Class(
                            id,
                            name,
                            price.toString(),
                            change_price.toString(),
                            change_percent,
                            time,
                            status
                        )
                    )

                }

                adapter(dataListss)

            },
            {

                noDataAnim()

                Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonArrayRequest)


    }

    fun loadingAnim(yesOrno : String){

        if (yesOrno=="yes"){

            binding.lottieAnimation.setAnimation(R.raw.loading)
            binding.lottieAnimation.visibility = View.VISIBLE
            binding.recyclerStock.visibility = View.GONE

        }else {

            //binding.lottieAnimation.setAnimation(R.raw.loading)
            binding.lottieAnimation.visibility = View.GONE
            binding.recyclerStock.visibility = View.VISIBLE

        }

    }fun noDataAnim(){

            binding.lottieAnimation.setAnimation(R.raw.no_data)
            binding.lottieAnimation.visibility = View.VISIBLE
            binding.recyclerStock.visibility = View.GONE


    }




    override fun onFavClick() {
        loadData()
    }

}