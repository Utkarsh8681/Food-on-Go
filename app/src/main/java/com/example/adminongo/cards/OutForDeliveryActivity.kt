package com.example.adminongo.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminongo.R
import com.example.adminongo.adapter.OutForDeliveryAdapter
import com.example.adminongo.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding : ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
binding.backBtn.setOnClickListener {
    onBackPressed()
    finish()
}
  val costName = listOf("utaksrsh","abhishek","aman")
  val status = listOf("Recieved","Not Recieved","Recieved")

        val adapter = OutForDeliveryAdapter(ArrayList(costName), ArrayList(status))
        binding.deliveryRecycleView.layoutManager = LinearLayoutManager(this)
        binding.deliveryRecycleView.adapter = adapter
    }
}