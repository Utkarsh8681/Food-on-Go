package com.example.foodongo

import android.location.Address
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.foodongo.Model.OrderDetails
import com.example.foodongo.databinding.ActivityPayoutBinding
import com.google.android.play.core.integrity.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.xml.sax.DTDHandler

class PayoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayoutBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var address: String
    private lateinit var totalAmount: String
    private lateinit var foodName: ArrayList<String>
    private lateinit var foodPrice: ArrayList<String>
    private lateinit var foodImage: ArrayList<String>
    private lateinit var foodDescription: ArrayList<String>
    private lateinit var foodIngridients: ArrayList<String>
    private lateinit var foodQuantity: ArrayList<Int>
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()

        setUserData()
        val intent = intent
        foodName = intent.getStringArrayListExtra("foodName") as ArrayList<String>
        foodPrice = intent.getStringArrayListExtra("foodPrice") as ArrayList<String>
        foodImage = intent.getStringArrayListExtra("foodImage") as ArrayList<String>
        foodDescription = intent.getStringArrayListExtra("foodDescription") as ArrayList<String>
        foodIngridients = intent.getStringArrayListExtra("foodIngridients") as ArrayList<String>
        foodQuantity = intent.getIntegerArrayListExtra("foodQuantity") as ArrayList<Int>

        totalAmount = calculateTotalAmmount().toString() + "$"
//        binding.totalPrice.isEnabled = false
        binding.totalPrice.setText(totalAmount)


        binding.placeOrder.setOnClickListener {

            name = binding.name.text.toString().trim()
            address = binding.address.text.toString().trim()
            phone = binding.phone.text.toString().trim()
            if(name.isBlank()||phone.isBlank()||address.isBlank()){
                   Toast.makeText(this, "Please Fill all the details🥲", Toast.LENGTH_SHORT).show()
               }
            else{
                placeOrder()
               }
//        removeItemFromCart()


        }
        binding.backBtn2.setOnClickListener {
            finish()
        }

    }

    private fun removeItemFromCart() {

        val cartItems =databaseReference.child("user").child(userId).child("catItems")
        cartItems.removeValue()
}

    private fun placeOrder() {

        userId = auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetals").push().key
        val orderDetail = OrderDetails(userId,name,foodName,foodImage,foodPrice,foodQuantity,address,phone,totalAmount,time,itemPushKey,false,false)
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetail).addOnSuccessListener {
            val bottomSheetDialogFragment = congratulationsFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, "Test")
            removeItemFromCart()
           addOrderToHistory(orderDetail)
        }
            .addOnFailureListener {
                Toast.makeText(this, "failed to place order😒", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetail: OrderDetails) {

        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetail.itemPushKey!!)
            .setValue(orderDetail).addOnSuccessListener {

            }
    }


    private fun calculateTotalAmmount(): Int{

            var totalAmmount = 0
            for(i in 0 until foodPrice.size){
                var price = foodPrice[i]
                val lastChar = price.last()
                val priceIntValue = if (lastChar == '$'){

                    price.dropLast(1).toInt()

                }else{
                    price.toInt()
                }
                var quantity = foodQuantity[i]
                totalAmmount += priceIntValue *quantity
            }
            return totalAmmount



    }


    private fun setUserData() {

        val user = auth.currentUser

        if (user != null) {
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val names = snapshot.child("name").getValue(String::class.java) ?: ""
                    val phones = snapshot.child("phone").getValue(String::class.java) ?: ""
                    val addresses = snapshot.child("address").getValue(String::class.java) ?: ""
//                    val ammount = snapshot.child("totalAmmount").getValue(String::class.java) ?: ""
                    binding.apply {
                        name.setText(names)
                        address.setText(addresses)
                        phone.setText(phones)
                        totalPrice.setText(totalAmount)

                    }

                }

                override fun onCancelled(error: DatabaseError) {


                }

            })

        }

    }

}