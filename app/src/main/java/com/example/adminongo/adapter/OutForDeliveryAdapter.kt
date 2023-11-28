package com.example.adminongo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminongo.databinding.OutForDeliveryItemBinding

class OutForDeliveryAdapter(private val CostumerName:ArrayList<String>,private val paymentStatus:ArrayList<String>): RecyclerView.Adapter<OutForDeliveryAdapter.DeliveryViewHolder>() {


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
                status.text = paymentStatus[position]
            }
        }

    }
}