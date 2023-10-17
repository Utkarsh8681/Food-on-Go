package com.example.foodongo.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodongo.databinding.PopularItemBinding

class PopularAdapter(private val items:List<String>, private val price:List<String>, private val image:List<Int>): RecyclerView.Adapter<PopularAdapter.popularViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): popularViewHolder {
        val binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return popularViewHolder(binding)
    //      return popularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: popularViewHolder, position: Int) {
//    val item = items[position]
//        val images = image[position]
//        val price = price[position]
//        holder.bind(item , images,price)
        holder.bind(position)
    }
    override fun getItemCount(): Int {
     return items.size
    }

   inner class popularViewHolder (private val binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(position: Int) {
           binding.apply {
            foodName.text = items[position]
               popularPrice.text =price[position]
               popularImg.setImageResource(image[position])

           }

       }
//       private val imageView = binding.popularImg
//        fun bind(item: String, images: Int, price: String) {
//            binding.foodName.text = item
//         binding.price.text = price
//         imageView.setImageResource(images)

        }

    }