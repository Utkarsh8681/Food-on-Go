package com.example.foodongo.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodongo.DetailsActivity
import com.example.foodongo.Model.MenuItem
import com.example.foodongo.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext:Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
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
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
val menuItem = menuItems[position]

// a intent to open details activity
            val intent  = Intent(requireContext , DetailsActivity::class.java).apply {
                putExtra("MenuItemName",menuItem.foodName)
                putExtra("MenuItemPrice",menuItem.foodPrice)
                putExtra("MenuItemImage",menuItem.foodImage)
                putExtra("MenuItemDescrioption",menuItem.description)
                putExtra("MenuItemIngridients",menuItem.ingridients)
            }
// START DETAILS ACTIVITY

            requireContext.startActivity(intent)

        }

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text  = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImg)

//
            }

        }

    }
    }

