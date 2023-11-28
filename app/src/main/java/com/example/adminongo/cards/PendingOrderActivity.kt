package com.example.adminongo.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminongo.R
import com.example.adminongo.adapter.PendingOrder
import com.example.adminongo.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val foodName = listOf("SandWhich","Momo", "Chowmien","pasta","Maggie")
        val quant  = listOf("2","2","2","2","2",)
        val foodimage = listOf( R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu1,)
        val adapter = PendingOrder(ArrayList(foodName),ArrayList(quant),ArrayList(foodimage))

        binding.pendingRecycle.layoutManager = LinearLayoutManager(this)
        binding.pendingRecycle.adapter = adapter
    }
}