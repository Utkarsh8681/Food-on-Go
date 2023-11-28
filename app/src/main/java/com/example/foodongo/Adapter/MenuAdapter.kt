package com.example.foodongo.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodongo.DetailsActivity
import com.example.foodongo.databinding.MenuItemBinding

class MenuAdapter(private val menuItems:MutableList<String>,private val menuItemsPrice:MutableList<String>,private val menuItemsImg:MutableList<Int>,private val requireContext:Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
private val itemClickListner : OnClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
       val binding =  MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItems.size
    inner class MenuViewHolder (private val binding:MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position =adapterPosition
                if (position !=RecyclerView.NO_POSITION ){
                    itemClickListner?.onItemClick(position)
                }
//                set on click listner to open details
                val intent = Intent(requireContext,DetailsActivity::class.java)
                intent.putExtra("MenuItemName",menuItems.get(position))
                intent.putExtra("MenuItemImage",menuItemsImg .get(position))
                requireContext.startActivity(intent)
            }
        }
        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text = menuItems[position]
                menuPrice.text  = menuItemsPrice[position]
                menuImg.setImageResource(menuItemsImg[position])

//
            }

        }

    }
    interface OnClickListener {
        fun onItemClick(position: Int)

        }
    }

