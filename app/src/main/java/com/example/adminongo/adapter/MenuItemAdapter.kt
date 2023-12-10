package com.example.adminongo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminongo.databinding.AllItemsItemBinding
import com.example.adminongo.model.addItems
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<addItems>,
    databaseReference: DatabaseReference,
    private val onDeleteClickListner :(position : Int) -> Unit
//    private val ItemName: List<String>,
//    private val Itemprice: ArrayList<String>,
//    private val ItemImage: ArrayList<Int>
) : RecyclerView.Adapter<MenuItemAdapter.itemViewHolder>() {

    private val itemQuantity = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        val binding =
            AllItemsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return itemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size
    inner class itemViewHolder(private val binding: AllItemsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantity[position]
                val menuItem = menuList[position]
                val uriString:String?= menuItem.foodImage
                val uri = Uri.parse(uriString)
                foodname.text =menuItem.foodName
                price.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(foodImage)

                quantityView.text = quantity.toString()
                minusBtn.setOnClickListener {
                    decreaseQuantity(position)
                }

                plusBtn.setOnClickListener {
                    increaseQuantity(position)
                }
                trash.setOnClickListener {
                    deleteItem(position)
                }
            }

        }

        private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)

        }

        private fun increaseQuantity(position: Int) {
            if(itemQuantity[position]<10){
                itemQuantity[position]++
                binding.quantityView.text = itemQuantity[position].toString()
            }

        }

        private fun decreaseQuantity(position: Int) {
         if(itemQuantity[position]>1){
             itemQuantity[position]--
             binding.quantityView.text = itemQuantity[position].toString()
         }
        }
    }
}