package com.anondo.dsebangladesh

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.anondo.dsebangladesh.databinding.ItemBinding

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

                this.txtCompany.text = it.name
                this.txtChange.text = it.change_price
                this.txtPrice.text = it.price
                this.txtChangePercent.text = it.change_percent

                if (it.change_price.toFloat()>0){
                    this.imgArrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_up_arrow))
                }else if (it.change_price.toDouble()==0.0){
                    imgArrow.imageTintList = ColorStateList.valueOf("#1E88E5".toColorInt())
                }else{
                    this.imgArrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down_arrow))
                    imgArrow.imageTintList = ColorStateList.valueOf("#C62828".toColorInt())
                }

            }

        }

    }

    override fun getItemCount(): Int {
        return stockData.size
    }

}