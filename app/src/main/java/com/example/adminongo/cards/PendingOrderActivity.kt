package com.example.adminongo.cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminongo.OrderDetailsActivity
import com.example.adminongo.adapter.PendingOrder
import com.example.adminongo.databinding.ActivityPendingOrderBinding
import com.example.adminongo.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(), PendingOrder.OnItemClicked {
    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    private var listOfItemName: MutableList<String> = mutableListOf()
    private var listOfItemePrice: MutableList<String> = mutableListOf()
    private var listOfItemImage: MutableList<String> = mutableListOf()
    private val listOfOrderItems: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//initialization of database and database reference
        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()

    }

    private fun getOrderDetails() {

        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItems.add(it)
                    }

                }
                addDataToRecyclerView()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataToRecyclerView() {

        for (orderItem in listOfOrderItems) {

//            add data to respective list of populating the recycleview
            orderItem.userName?.let { listOfItemName.add(it) }
            orderItem.totalPrice?.let { listOfItemePrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach {
                listOfItemImage.add(it)
            }

        }
        setAdapter()

    }

    private fun setAdapter() {
        binding.pendingRecycle.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrder(this,listOfItemName,listOfItemePrice,listOfItemImage,this)
        binding.pendingRecycle.adapter = adapter

    }

    override fun onItemClickedListner(position: Int) {

        val intent = Intent(this,OrderDetailsActivity::class.java)
        val userDetails = listOfOrderItems[position]
        intent.putExtra("UserOrderDetails",userDetails)
        startActivity(intent)

    }

    override fun onItemAcceptClickListner(position: Int) {
//handle item acceptance
val chilItemPushKey = listOfOrderItems[position].itemPushKey
        val clickItemOrderReference = chilItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
clickItemOrderReference?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)
    }



    override fun onItemDispatchClickListner(position: Int) {
        //handle item acceptance
        val dispachItemPushKey = listOfOrderItems[position].itemPushKey
        val dispatchOrderReference = database.reference.child("CompletedOrder").child(dispachItemPushKey!!)
        dispatchOrderReference.setValue(listOfOrderItems[position])
            .addOnSuccessListener {
                deleteThisItemFeomOrderDetail(dispachItemPushKey)
            }

    }

    private fun deleteThisItemFeomOrderDetail(dispachItemPushKey: String) {

        val orderDetailsItemReference = database.reference.child("OrderDetails").child(dispachItemPushKey)
        orderDetailsItemReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Order Is Dispatched", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "order is not dispatched ", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateOrderAcceptStatus(position: Int) {
//update order acceptance

        val userIdofClickeditem = listOfOrderItems[position].userUid
        val pushKeyOfClickedItem = listOfOrderItems[position].itemPushKey
        val buyHistoryReference = database.reference.child("user").child(userIdofClickeditem!!).child("BuyHistory").child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("orderAccepted").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)

    }
}