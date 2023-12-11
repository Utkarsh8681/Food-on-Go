package com.example.adminongo.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminongo.R
import com.example.adminongo.databinding.ActivityAdminProfileBinding
import com.example.adminongo.databinding.OrderDetailBinding
import com.example.adminongo.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private  val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        binding.saveInfo.setOnClickListener {
//            updateUserData()
//        }
        binding.backBtn.setOnClickListener {
            finish()
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")


//        binding.saveInfo.isEnabled = false

//        retrieveUserData()
    }

    private fun updateUserData() {
      var updateName =   binding.name.text.toString()
       var updateEmail =  binding.email.text.toString()
       var updatePassword=  binding.password.text.toString()
      var updateAddress  =  binding.address.text.toString()
       var updatePhone =  binding.phoneNo.text.toString()
        val currentuserUid = auth.currentUser?.uid
        if(currentuserUid != null){
            val userReference =  adminReference.child(currentuserUid!!)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("address").setValue(updateAddress)
            Toast.makeText(this, "Profile Updated Sucessfully", Toast.LENGTH_SHORT).show()
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }




        else
        {
            Toast.makeText(this, "update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrieveUserData() {

        val currentUserUid = auth.currentUser?.uid
        val userRefernce = adminReference.child(currentUserUid!!)


        userRefernce.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
                if (snapshot.exists()){
                    var ownerName = snapshot.child("name").getValue()
                    var email = snapshot.child("email").getValue()
                    var password = snapshot.child("password").getValue()
                    var address = snapshot.child("address").getValue()
                    var phone = snapshot.child("phone").getValue()
                    setDataToTextView(ownerName,email,password,address,phone)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataToTextView(
        ownerName: Any? ,
        email: Any?,
        password: Any? ,
        address: Any?,
        phone: Any?
    ) {

        binding.name.setText(ownerName.toString())
        binding.email.setText(email.toString())
        binding.password.setText(password.toString())
        binding.address.setText(address.toString())
        binding.phoneNo.setText(phone.toString())
    }


}