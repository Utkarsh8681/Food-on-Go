package com.example.foodongo.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodongo.DetailsActivity
import com.example.foodongo.databinding.PopularItemBinding

class PopularAdapter(private val items:List<String>, private val price:List<String>, private val image:List<Int>,private val requireContext: Context): RecyclerView.Adapter<PopularAdapter.popularViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): popularViewHolder {
//        val binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//            return popularViewHolder(binding)
          return popularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: popularViewHolder, position: Int) {
    val item = items[position]
        val images = image[position]
        val price = price[position]
//        holder.bind(item, images,price)
        holder.bind(position)
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, DetailsActivity::class.java)
           intent.putExtra("MenuItemName",items.get(position))
           intent.putExtra("MenuItemImage",image.get(position))
           requireContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
     return items.size
    }

   inner class popularViewHolder (private val binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(position: Int) {
           binding.apply {
            menuFoodName.text = items[position]
               menuPrice.text =price[position]
               menuImg.setImageResource(image[position])

           }
//
       }
//       private val imageView = binding.popularImg
//        fun bind(item: String, images: Int, price: String) {
//            binding.foodName.text = item
//         binding.price.text = price
//         imageView.setImageResource(images)

        }

    }