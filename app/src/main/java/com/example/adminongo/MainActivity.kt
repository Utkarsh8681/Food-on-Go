package com.example.adminongo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminongo.cards.AddItemsActivity
import com.example.adminongo.cards.AdminProfileActivity
import com.example.adminongo.cards.AllItemActivity
import com.example.adminongo.cards.CreateNewProfileActivity
import com.example.adminongo.cards.OutForDeliveryActivity
import com.example.adminongo.cards.PendingOrderActivity
import com.example.adminongo.databinding.ActivityMainBinding
import com.example.adminongo.login.LoginActivity
import com.example.adminongo.login.SignUpActivity
import com.example.adminongo.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
     private val binding : ActivityMainBinding by lazy {
         ActivityMainBinding.inflate(layoutInflater)
     }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completedOrderReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addMenu.setOnClickListener {
            val intent = Intent(this, AddItemsActivity::class.java)
            startActivity(intent)

        }
        binding.AllItemMenu.setOnClickListener {
            val intent = Intent(this, AllItemActivity::class.java)
            startActivity(intent)

        }

//        binding.profileActivity.setOnClickListener {
//            val intent = Intent(this, AdminProfileActivity::class.java)
//            startActivity(intent)
//
//        }
        binding.profileActivity.setOnClickListener {
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.newUser.setOnClickListener {
            val intent = Intent(this, CreateNewProfileActivity::class.java)
            startActivity(intent)

        }
        binding.orderDispatch.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)

        }
        binding.logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)


        }

           binding.pendingText.setOnClickListener {
                val intent = Intent(this,PendingOrderActivity::class.java)
                startActivity(intent)


        }
        binding.orderDispatch.setOnClickListener{
            val intent = Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)

        }
        binding.logout.setOnClickListener {
        auth.signOut()
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        pendingOrder()

        completedOrders()

        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {
        var listOfTotalPay = mutableListOf<Int>()
        completedOrderReference = database.reference.child("CompletedOrder")
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    var completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.totalPrice?.replace("$","")?.toIntOrNull()
                        ?.let { i ->
                            listOfTotalPay.add(i)
                        }
                }
                binding.wholeTimeEarning.text = listOfTotalPay.sum().toString() + "$"

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun completedOrders() {
        var pendingOrderCount = 20
        var pendingOrderReference = database.reference.child("CompletedOrder")
        pendingOrderReference.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderCount = snapshot.childrenCount.toInt()
                binding.completedCount.text = pendingOrderCount.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun pendingOrder() {

        database = FirebaseDatabase.getInstance()

        var pendingOrderCount = 0
        var pendingOrderReference = database.reference.child("OrderDetails")
        pendingOrderReference.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderCount = snapshot.childrenCount.toInt()
                binding.pendingCount.text = pendingOrderCount.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


}