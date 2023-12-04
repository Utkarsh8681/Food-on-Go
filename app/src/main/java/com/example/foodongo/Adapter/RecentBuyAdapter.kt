package com.example.foodongo.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodongo.databinding.RecentOrdersBinding

class RecentBuyAdapter(
    private var context: Context,
    private var foodNameList: ArrayList<String>,
    private var foodImageList:ArrayList<String>,
    private var foodQuantityList: ArrayList<Int>,
    private var foodPriceList: ArrayList<String>

    ) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {

        val binding = RecentOrdersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = foodNameList.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }
  inner  class RecentViewHolder(private val binding : RecentOrdersBinding):RecyclerView.ViewHolder(binding.root) {
      fun bind(position: Int) {

          binding.apply {
            recentItemName.text = foodNameList[position]
            recentPrice.text = foodPriceList[position]
            qunat.text = foodQuantityList[position].toString()
              val uriString = foodImageList[position]
              val uri  = Uri.parse(uriString)
              Glide.with(context).load(uri).into(recentImage)
          }
      }

  }

}