package com.example.foodongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.RecentBuyAdapter
import com.example.foodongo.Model.OrderDetails
import com.example.foodongo.databinding.ActivityRecentOrderItemBinding

class RecentOrderItem : AppCompatActivity() {

    private val binding: ActivityRecentOrderItemBinding by lazy {
        ActivityRecentOrderItemBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames : ArrayList<String>
    private lateinit var allFoodPrice : ArrayList<String>
    private lateinit var allFoodQunantity : ArrayList<Int>
    private lateinit var allFoodImage : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recentOrderItems = intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems ?.let {orderDetails ->
            if (orderDetails.isNotEmpty()){
                val recentOrderItem = orderDetails[0]
                allFoodNames = recentOrderItem.foodNames as ArrayList<String>
                allFoodImage = recentOrderItem.foodImages as ArrayList<String>
                allFoodPrice = recentOrderItem.foodPrices as ArrayList<String>
                allFoodQunantity = recentOrderItem.foodQuantities as ArrayList<Int>

            }
        }
        setAdapter()
    }

    private fun setAdapter() {

        val rv = binding.recentRecycleView
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this,allFoodNames,allFoodImage,allFoodQunantity,allFoodPrice)
        rv.adapter = adapter
    }
}