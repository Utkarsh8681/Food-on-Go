package com.example.foodongo.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodongo.databinding.NotificationItemBinding

class NotificationAdapter(private val notification : ArrayList<String>,private val notificationImage: ArrayList<Int>): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationViewHolder(binding)
    }



    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
    holder.bind(position)
    }
    override fun getItemCount(): Int =notification.size
   inner class NotificationViewHolder(private val binding: NotificationItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                notificationText.text = notification[position]
                notificationImg.setImageResource(notificationImage[position])
            }


        }

    }
}