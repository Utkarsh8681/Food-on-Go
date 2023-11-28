package com.example.adminongo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminongo.databinding.PendingOrderItemBinding

class PendingOrder(private val itemNames: ArrayList<String>,private val quantity: ArrayList<String>,private val itemImage:ArrayList<Int>): RecyclerView.Adapter<PendingOrder.PendingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = itemNames.size


    inner class PendingViewHolder(private val binding: PendingOrderItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                itemName.text = itemNames[position]
                quantityNo.text = quantity[position]
                itemImageView.setImageResource(itemImage[position])
            }
        }

    }
}