package com.example.adminongo.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminongo.databinding.OutForDeliveryItemBinding

class OutForDeliveryAdapter(private val CostumerName:MutableList<String>,private val paymentStatus:MutableList<Boolean>): RecyclerView.Adapter<OutForDeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = OutForDeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int  = CostumerName.size

    inner class DeliveryViewHolder(private val binding: OutForDeliveryItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                costumerNam.text = CostumerName[position]
                if (paymentStatus[position] == true){
                    status.text = "Recieved"
                }
                else{
                    status.text = "NotRecieved"
                }

                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED
                )
                status.setTextColor(colorMap[paymentStatus[position]]?:Color.BLACK)
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[paymentStatus[position]]?:Color.BLACK)
            }
        }

    }
}