package com.example.foodongo.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodongo.databinding.PreviousBuyBinding

class BuyAgainAdapter(
    private val buyAgainFoodName: MutableList<String>,
    private val buyAgainFoodPrice: MutableList<String>,
    private val buyAgainImage: MutableList<String>,
    private val requireContext: Context
) : RecyclerView.Adapter<BuyAgainAdapter.BuyHolderView>() {

    override fun onBindViewHolder(holder: BuyHolderView, position: Int) {
        holder.bind(
            buyAgainFoodName[position],
            buyAgainFoodPrice[position],
            buyAgainImage[position]
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyHolderView {
        val binding = PreviousBuyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyHolderView(binding)
    }


    override fun getItemCount(): Int = buyAgainFoodName.size
    inner class BuyHolderView(private val binding: PreviousBuyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: String) {
            binding.preName.text = foodName
            binding.BuyAgainPrice.text = foodPrice
            val uriString = foodImage
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.preImage)
binding.buyAgain.setOnClickListener {  }
        }



    }
}