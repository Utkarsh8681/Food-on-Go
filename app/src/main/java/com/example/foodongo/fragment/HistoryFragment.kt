package com.example.foodongo.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodongo.Adapter.BuyAgainAdapter
import com.example.foodongo.Model.OrderDetails
import com.example.foodongo.R
import com.example.foodongo.RecentOrderItem
import com.example.foodongo.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
//        retrieve the order data history

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        // Inflate the layout for this fragment
        retrieveOrderHistory()
        setPreviousDataInRecyclerView()


//        binding.recentBoughtItem.setOnClickListener {
//            seeItemsRecentBuy()

//            startActivity(intent)
        binding.updateStatus.setOnClickListener {
            updateOrderStatus()
            binding.updateStatus.visibility = View.INVISIBLE
        }

//        }
        return binding.root
    }

    private fun updateOrderStatus() {

        val itemPushKey =  listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)

    }

    private fun seeItemsRecentBuy() {

        listOfOrderItem.firstOrNull()?.let { recentBuy ->
            val intent = Intent(requireContext(), RecentOrderItem::class.java)
            intent.putExtra("RecentBuyOrderItem", listOfOrderItem)
            startActivity(intent)
        }
    }

    private fun retrieveOrderHistory() {
        binding.recentBoughtItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""

        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val sortingOrder = buyItemReference.orderByChild("currentTime")

        sortingOrder.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (buySnapShot in snapshot.children) {
                    val buyHistoryItem = buySnapShot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    setUpDataInRecentBuy()
                    setPreviousDataInRecyclerView()
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun setUpDataInRecentBuy() {
        binding.recentBoughtItem.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                buyAgainName.text = it.foodNames?.firstOrNull() ?: ""
                BuyAgainPrice.text = it.foodPrices?.firstOrNull() ?: ""
                val image = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainImage)

                val isOrderIsAccepted = listOfOrderItem[0].orderAccepted
                Log.d("tag", "setUpDataInRecentBuy: $isOrderIsAccepted")
                if(isOrderIsAccepted){
                    status.background.setTint(Color.GREEN)
                    updateStatus.visibility = View.VISIBLE
                }

                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
//                    setUpDataInRecentBuy()
//                    setPreviousDataInRecyclerView()
                }
            }
        }


    }

    private fun setPreviousDataInRecyclerView() {
        val buyAgainFood = mutableListOf<String>()
        val buyAgainPrice = mutableListOf<String>()
        val buyAgainImage = mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let {
                buyAgainFood.add(it)
                listOfOrderItem[i].foodPrices?.firstOrNull()?.let {
                    buyAgainPrice.add(it)
                    listOfOrderItem[i].foodImages?.firstOrNull()?.let {
                        buyAgainImage.add(it)
                    }
                }
                val rv = binding.historyRecycle
                rv.layoutManager = LinearLayoutManager(requireContext())
                buyAgainAdapter =
                    BuyAgainAdapter(buyAgainFood, buyAgainPrice, buyAgainImage, requireContext())
                rv.adapter = buyAgainAdapter
            }


        }
    }
}

private fun Intent.putExtra(s: String, listOfOrderItem: MutableList<OrderDetails>) {


}
