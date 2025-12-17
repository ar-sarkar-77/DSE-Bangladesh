package com.anondo.dsebangladesh.views.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.anondo.dsebangladesh.R
import com.anondo.dsebangladesh.data.onmodels.Stock_Data_Class
import com.anondo.dsebangladesh.databinding.ActivitySetNotificationBinding
import org.json.JSONArray

class SetNotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivitySetNotificationBinding
    lateinit var urlOpen : String
    lateinit var urlClose : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id =  intent.getIntExtra("id", 0)
        var name =  intent.getStringExtra("name")
        var price =  intent.getStringExtra("price")
        var change_price =  intent.getStringExtra("change_price")
        var change_percent =  intent.getStringExtra("change_percent")
        var time =  intent.getStringExtra("time")

        urlOpen = "https://arsarkar.xyz/apps/get_data_in_ten_am.php?id=$id"
        urlClose = "https://arsarkar.xyz/apps/get_data_in_three_pm.php?id=$id"


        var parts = time?.split(" ")

        var yearMonthDate = parts?.get(0)
        var secMinHour = parts?.get(1)

        binding.apply {

            tvCompanyName.text = name
            tvSharePrice.text = "৳ $price"

            if (change_price!!>="0"){
                tvChangePrice.text = "+"+change_price
                tvChangePercent.text = "(+$change_percent%)"
            }else{
                tvChangePrice.text = change_price
                tvChangePercent.text = "($change_percent%)"
            }

            tvLastUpdated.text = "Last updated: $secMinHour"

        }

        loadData( urlOpen)
        loadData(urlClose)


    }

    private fun loadData(url : String){

        val queue = Volley.newRequestQueue(this)

        var jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { responce: JSONArray ->

                binding.lottieAnimation.visibility = View.GONE
                binding.allData.visibility = View.VISIBLE

                if (url==urlOpen){

                    for (i in 0..responce.length() - 1) {

                        var jsonObject = responce.getJSONObject(i)

                        val id = jsonObject.getInt("id")  // int
                        val name = jsonObject.getString("name") // string
                        val price = jsonObject.getDouble("price").toFloat() // float
                        val change_price = jsonObject.getDouble("change_price").toFloat() // float
                        val change_percent = jsonObject.getString("change_percent") // string
                        val time = jsonObject.getString("time") // timestamp string
                        val status = jsonObject.getInt("status") // status int

                        binding.apply {

                            tvTodayCompanyName.text = name
                            tvTodayPrice.text = "৳ $price"

                            if (change_price.toString()>="0"){
                                tvTodayChangePrice.text = "+"+change_price
                                tvTodayChangePercent.text = "(+$change_percent%)"
                            }else{
                                tvTodayChangePrice.text = change_price.toString()
                                tvTodayChangePercent.text = change_percent
                            }

                        }
                    }


                }else{

                    for (i in 0..responce.length() - 1) {

                        var jsonObject = responce.getJSONObject(i)

                        val id = jsonObject.getInt("id")  // int
                        val name = jsonObject.getString("name") // string
                        val price = jsonObject.getDouble("price").toFloat() // float
                        val change_price = jsonObject.getDouble("change_price").toFloat() // float
                        val change_percent = jsonObject.getString("change_percent") // string
                        val time = jsonObject.getString("time") // timestamp string
                        val status = jsonObject.getInt("status") // status int

                        binding.apply {

                            tvPrevCompanyName.text = name
                            tvPrevPrice.text = "৳ $price"

                            if (change_price.toString()>="0"){
                                tvPrevChangePrice.text = "+"+change_price
                                tvPrevChangePercent.text = "(+$change_percent%)"
                            }else{
                                tvPrevChangePrice.text = change_price.toString()
                                tvPrevChangePercent.text = change_percent
                            }

                        }
                    }


                }


            }, {
                Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonArrayRequest)


    }

}