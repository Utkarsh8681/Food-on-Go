package com.example.adminongo.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminongo.R
import com.example.adminongo.adapter.OutForDeliveryAdapter
import com.example.adminongo.databinding.ActivityOutForDeliveryBinding
import com.example.adminongo.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding : ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrderList : ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
binding.backBtn.setOnClickListener {
    onBackPressed()
    finish()
}
        retrieveCompleteOrderDetails()



    }

    private fun retrieveCompleteOrderDetails() {

        database = FirebaseDatabase.getInstance()
        val completeOrderReference = database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completeOrderReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                clear the list before populating
                listOfCompleteOrderList.clear()
                for (orderSnapshot in snapshot.children){
                    val completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)

                    }

                }
                listOfCompleteOrderList.reverse()

                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataIntoRecyclerView() {

//        initialization list to hold costumer Name and payment status

        val coustumerName = mutableListOf<String>()
        val moneyStatus  = mutableListOf<Boolean>()

        for (order in  listOfCompleteOrderList){
            order.userName?.let {
                coustumerName.add(it)

            }
            moneyStatus.add(order.paymentReceived)
        }
        val adapter = OutForDeliveryAdapter(coustumerName,moneyStatus)
        binding.deliveryRecycleView.layoutManager = LinearLayoutManager(this)
        binding.deliveryRecycleView.adapter = adapter
    }
}