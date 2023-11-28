package com.example.foodongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.foodongo.databinding.ActivityPayoutBinding

class PayoutActivity : AppCompatActivity() {
    lateinit var  binding: ActivityPayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityPayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

         binding.placeOrder.setOnClickListener {
             val bottomSheetDialogFragment = congratulationsFragment()
             bottomSheetDialogFragment.show(supportFragmentManager,"Test")

    }
    }

}