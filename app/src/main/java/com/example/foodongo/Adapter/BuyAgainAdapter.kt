package com.example.foodongo.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodongo.databinding.PreviousBuyBinding

class BuyAgainAdapter(private val buyAgainFoodName:ArrayList<String>,private val buyAgainFoodPrice: ArrayList<String>,private val buyAgainImage:ArrayList<Int>): RecyclerView.Adapter<BuyAgainAdapter.BuyHolderView>() {

    override fun onBindViewHolder(holder: BuyHolderView, position: Int) {
   holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],buyAgainImage[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyHolderView {
val binding =  PreviousBuyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyHolderView(binding)
    }



    override fun getItemCount(): Int = buyAgainFoodName.size
    class BuyHolderView (private val binding: PreviousBuyBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(foodName: String, foodPrice: String, foodImage: Int) {
            binding.preName.text = foodName
            binding.prePrice.text = foodPrice
            binding.preImage.setImageResource(foodImage)
        }


    }
}