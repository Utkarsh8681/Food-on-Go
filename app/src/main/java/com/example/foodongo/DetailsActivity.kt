package com.example.foodongo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodongo.Model.Cart
import com.example.foodongo.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foodName : String? = null
    private var foodImage : String? = null
    private var foodPrice : String? =  null
    private var description : String? = null
    private var ingridients : String? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        foodName = intent.getStringExtra("MenuItemName")
        foodPrice =intent.getStringExtra("MenuItemPrice")
        description = intent.getStringExtra("MenuItemDescrioption")
        ingridients = intent.getStringExtra("MenuItemIngridients")
        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding){
            detailFoodName.text =foodName
            detalDescription.text = description
            detailIngridients.text = ingridients
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)

        }

//        val foodName = intent.getStringExtra("MenuItemName")
//        val foodImage = intent.getIntExtra("MenuItemImage",0)
//        binding.detailFoodName.text = foodName
//        binding.detailFoodImage.setImageResource(foodImage)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.addToCart.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid

//        create a cart items object
        val cartItem = Cart(foodName.toString(),foodPrice.toString(),description.toString(),foodImage.toString(),ingridients.toString() ,1)

//        save data of cart item to firebase
        if (userId != null) {
            database.child("user").child(userId).child("catItems").push().setValue(cartItem).addOnSuccessListener {
                Toast.makeText(this, "Item added to cart sucessfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

}