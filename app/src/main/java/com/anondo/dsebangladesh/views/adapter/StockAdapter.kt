package com.anondo.dsebangladesh.views.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.anondo.dsebangladesh.R
import com.anondo.dsebangladesh.data.offmodels.StockData
import com.anondo.dsebangladesh.data.onmodels.Stock_Data_Class
import com.anondo.dsebangladesh.databinding.ItemBinding
import com.anondo.dsebangladesh.reducecode.ReduceCode

class StockAdapter(
    var context: Context,
    var stockData: MutableList<Stock_Data_Class>
) : RecyclerView.Adapter<StockAdapter.ViewHolder>(){

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

                this.txtCompany.text = name
                this.txtChange.text = change_price
                this.txtPrice.text = "Price "+price
                this.txtChangePercent.text = change_percent

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

                this.imgFav.tag = "notFav"

                this.imgFav.setOnClickListener {

                    if (this.imgFav.tag == "notFav"){

                        this.imgFav.tag = "Fav"

                        this.imgFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_filled))

                        var dao = ReduceCode.database(context)

                        var stockData = StockData( id , name , price , change_price , change_percent , time , "1" )

                        dao.Add_Task(stockData)

                    }else{

                        this.imgFav.tag = "notFav"

                        this.imgFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star))

                        var dao = ReduceCode.database(context)

                        var stockData = StockData( id , name , price , change_price , change_percent , time , "1" )

                        dao.Delete_Data(stockData)

                    }

                }

            }

        }

    }

    override fun getItemCount(): Int {
        return stockData.size
    }

}