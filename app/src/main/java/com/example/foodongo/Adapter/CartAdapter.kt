package com.example.foodongo.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodongo.databinding.CartItemBinding

class CartAdapter(private val cartItems:MutableList<String>,private val cartItemPrice:MutableList<String>,private val CartImage:MutableList<Int> ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
   private val itemQuantity = IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  CartViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = cartItems.size

   inner  class CartViewHolder (private val binding: CartItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply{
                val quantity = itemQuantity[position]
                itemName.text = cartItems[position]
                itemPrice.text = cartItemPrice[position]
                cartImage.setImageResource(CartImage[position])
                itemCount.text = quantity.toString()

            }
        }

    }
}