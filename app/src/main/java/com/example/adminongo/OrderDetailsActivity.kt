package com.example.adminongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminongo.adapter.OrderDetailsAdapter
import com.example.adminongo.databinding.ActivityOrderDetailsBinding
import com.example.adminongo.model.OrderDetails
import com.google.firebase.database.FirebaseDatabase

class OrderDetailsActivity : AppCompatActivity() {
    private  val binding : ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var userName : String ?= null
    private var phone : String ?= null
    private var address : String ?= null
    private  var foodPrice : ArrayList<String> = arrayListOf()
    private  var foodName : ArrayList<String> = arrayListOf()
    private  var foodImage : ArrayList<String> = arrayListOf()
    private  var foodQuantity : ArrayList<Int> = arrayListOf()
    private var totalPrices : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDataFromIntent()
    }

    private fun getDataFromIntent() {

        val recivedOrderDetail = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        recivedOrderDetail.let { orderDetails ->
            userName = recivedOrderDetail.userName
            phone = recivedOrderDetail.phoneNumber
            address = recivedOrderDetail.address
            foodImage = recivedOrderDetail.foodImages as ArrayList<String>
            foodPrice =recivedOrderDetail.foodPrices as ArrayList<String>
            foodName = recivedOrderDetail.foodNames as ArrayList<String>
            foodQuantity = recivedOrderDetail.foodQuantities as ArrayList<Int>
            totalPrices = recivedOrderDetail.totalPrice as ArrayList<String>
            setUserDetails()
            setAdapter()

        }

    }

    private fun setAdapter() {

        binding.orderDetailRecycleView.layoutManager = LinearLayoutManager(this)
        val adapter =OrderDetailsAdapter(this,foodName,foodPrice,foodQuantity,foodImage)
        binding.orderDetailRecycleView.adapter = adapter
    }

    private fun setUserDetails() {

        binding.name.text = userName
        binding.address.text = address
        binding.phone.text = phone
        binding.totalPay.text = totalPrices.toString()

    }
}