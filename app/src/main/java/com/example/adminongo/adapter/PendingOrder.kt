package com.example.adminongo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminongo.databinding.PendingOrderItemBinding

class PendingOrder(
    private val context: Context,
    private val itemNames: MutableList<String>,
    private val quantity: MutableList<String>,
    private val itemImage: MutableList<String>,
    private val itemClicked : OnItemClicked
) : RecyclerView.Adapter<PendingOrder.PendingViewHolder>() {
interface OnItemClicked{
    fun onItemClickedListner(position: Int)
    fun onItemAcceptClickListner(position: Int)
    fun onItemDispatchClickListner(position: Int)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        val binding =
            PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemNames.size


    inner class PendingViewHolder(private val binding: PendingOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private  var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                itemName.text = itemNames[position]
                quantityNo.text = quantity[position]
                var uriString = itemImage[position]
                var  uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(itemImageView)

                pendingAccept.apply {
                    if(!isAccepted){
                        text ="Accept"
                    }
                    else{
                        text = "Dispatch"
                    }
                    setOnClickListener { 
                        if(!isAccepted){
                            text = "Dispatch"
                            isAccepted = true
                            Toast.makeText(context, "Order Accepted", Toast.LENGTH_SHORT).show()
                            itemClicked.onItemAcceptClickListner(position)
                        }
                        else{
                            itemNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            Toast.makeText(context, "Order Is Dispatched", Toast.LENGTH_SHORT).show()
                            itemClicked.onItemDispatchClickListner(position)
                        }
                    }


                }
                itemView.setOnClickListener{
                    itemClicked.onItemClickedListner(position)

                }

            }
        }

    }
}