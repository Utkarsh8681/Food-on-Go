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
             minusBtn.setOnClickListener {
decreaseQuantity(position)
             }
                plusbtn.setOnClickListener {
increaseQuantity(position)
                }
                trashbtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        delete(position)
                    }
delete(position)
                }


            }
        }
      private fun decreaseQuantity(position: Int){
           if (itemQuantity[position] > 1){
               itemQuantity[position]--
              binding.itemCount.text = itemQuantity[position].toString()
           }

       }
      private fun increaseQuantity(position: Int){
           if (itemQuantity[position] < 10){
               itemQuantity[position]++
               binding.itemCount.text = itemQuantity[position].toString()
           }

       }
       private fun delete(position: Int){
           cartItemPrice.removeAt(position)
           cartItems.removeAt(position)
           CartImage.removeAt(position)
           notifyItemRemoved(position)
           notifyItemRangeChanged(position,cartItems.size)
       }
    }
}