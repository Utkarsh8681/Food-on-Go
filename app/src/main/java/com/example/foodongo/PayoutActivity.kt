package com.example.foodongo

import android.location.Address
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.foodongo.databinding.ActivityPayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.xml.sax.DTDHandler

class PayoutActivity : AppCompatActivity() {
    lateinit var  binding: ActivityPayoutBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name : String
    private lateinit var phone : String
    private lateinit var address : String
    private lateinit var totalAmmount  : String
    private lateinit var foodName : ArrayList<String>
    private lateinit var foodPrice : ArrayList<String>
    private lateinit var foodImage : ArrayList<String>
    private lateinit var foodDescription : ArrayList<String>
    private lateinit var foodIngridients : ArrayList<String>
    private lateinit var foodQuantity : ArrayList<String>
    private lateinit var userId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityPayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        setUserData()

         binding.placeOrder.setOnClickListener {
             val bottomSheetDialogFragment = congratulationsFragment()
             bottomSheetDialogFragment.show(supportFragmentManager,"Test")

    }
    }

    private fun setUserData() {

        val user = auth.currentUser

        if(user != null){
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)

            userReference.addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val names = snapshot.child("name").getValue(String::class.java)?:""
                    val phones = snapshot.child("phone").getValue(String::class.java)?:""
                    val addresses = snapshot.child("address").getValue(String::class.java)?:""
                    val ammount = snapshot.child("totalAmmoun").getValue(String::class.java)?:""
                    binding.apply {
                        name.setText(names)
                        address.setText(addresses)
                        phone.setText(phones)
                        totalPrice.setText(ammount)

                    }

                }

                override fun onCancelled(error: DatabaseError) {


                }

            })

        }

    }

}