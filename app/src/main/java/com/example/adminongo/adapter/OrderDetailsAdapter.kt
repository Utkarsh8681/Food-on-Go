package com.example.adminongo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminongo.databinding.OrderDetailBinding

class OrderDetailsAdapter(
    private var context: Context,
    private val foodName: ArrayList<String>,
    private val foodPrice: ArrayList<String>,
    private val foodQuantity: ArrayList<Int>,
    private val foodImage: ArrayList<String>,
):RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
val binding = OrderDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)

    }

    override fun getItemCount(): Int = foodName.size

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }
   inner class OrderDetailsViewHolder(private val binding: OrderDetailBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.apply {
                recentItemName.text = foodName[position]
                recentPrice .text = foodPrice[position]
                qunat.text = foodQuantity[position].toString()
                val uriString = foodImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(recentImage)

            }
        }

    }
}