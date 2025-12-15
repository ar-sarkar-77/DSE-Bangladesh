package com.anondo.dsebangladesh.views.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anondo.dsebangladesh.R
import com.anondo.dsebangladesh.data.offmodels.StockData
import com.anondo.dsebangladesh.data.onmodels.Stock_Data_Class
import com.anondo.dsebangladesh.databinding.ItemBinding
import com.anondo.dsebangladesh.reducecode.ReduceCode

class StockAdapter(
    var handleUser : handleUserClick,
    var context: Context,
    var stockData: MutableList<Stock_Data_Class>
) : RecyclerView.Adapter<StockAdapter.ViewHolder>(){

    interface handleUserClick{
        fun onFavClick()
    }


    class ViewHolder(var binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        var binding = ItemBinding.inflate(LayoutInflater.from(context) , parent , false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        holder.binding.apply {

            stockData[position].let {

                var id = it.id
                var name = it.name
                var price = it.price
                var change_price = it.change_price
                var change_percent = it.change_percent
                var time = it.time
                var status = it.status

                this.txtCompany.text = name
                this.txtChange.text = change_price
                this.txtPrice.text = "Price "+price
                this.txtChangePercent.text = change_percent

                if (status==0){
                    this.imgFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star))
                }else{
                    this.imgFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_filled))
                }

                if (it.change_price.toFloat()>0){

                    this.imgArrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_up_arrow))
                    imgArrow.imageTintList = ColorStateList.valueOf("#2E7D32".toColorInt())
                    this.txtChange.setTextColor("#2E7D32".toColorInt())
                    this.txtChangePercent.setTextColor("#2E7D32".toColorInt())

                }else if (it.change_price.toDouble()==0.0){

                    imgArrow.imageTintList = ColorStateList.valueOf("#1E88E5".toColorInt())
                    imgArrow.imageTintList = ColorStateList.valueOf("#1E88E5".toColorInt())
                    this.txtChange.setTextColor("#1E88E5".toColorInt())
                    this.txtChangePercent.setTextColor("#1E88E5".toColorInt())

                }else{

                    this.imgArrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down_arrow))
                    imgArrow.imageTintList = ColorStateList.valueOf("#C62828".toColorInt())
                    this.txtChange.setTextColor("#C62828".toColorInt())
                    this.txtChangePercent.setTextColor("#C62828".toColorInt())

                }

                this.imgFav.setOnClickListener {

                    if (status==0){

                        this.imgFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_filled))

                        update(id , 1)


                    }else{

                        this.imgFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star))

                        update(id , 0)

                    }

                }

            }

        }

    }

    override fun getItemCount(): Int {
        return stockData.size
    }

    fun update(id : Int , status : Int){

        var url = "https://arsarkar.xyz/apps/update.php?id=$id&status=$status"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->

                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

            },
            {

            })

        val queue : RequestQueue = Volley.newRequestQueue(context)
        queue.add(stringRequest)

        Handler(Looper.getMainLooper()).postDelayed({

            handleUser.onFavClick()

            }, 3000) // 6000 ms = 2 seconds


    }


}
