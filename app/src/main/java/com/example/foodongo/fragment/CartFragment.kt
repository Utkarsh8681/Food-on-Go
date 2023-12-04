package com.example.foodongo.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.CartAdapter
import com.example.foodongo.Model.Cart
import com.example.foodongo.PayoutActivity
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentCartBinding
import com.example.foodongo.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrice: MutableList<String>
    private lateinit var foodDescription: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngridients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()


        retrieveItems()




        binding.proceedBtn.setOnClickListener {

            getOrderDetails()
        }
        return binding.root
    }

    private fun getOrderDetails() {
        val orderIdReference = database.reference.child("user").child(userId).child("catItems")
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodIngridients = mutableListOf<String>()
//        get item qunantity
        val foodItemQuantities = cartAdapter.getUpdatedItemsQuantitiy()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val orderItems = foodSnapshot.getValue(Cart::class.java)
//        add item details into list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodIngridient?.let { foodIngridients.add(it) }

                }
                orderNow(
                    foodName,
                    foodPrice,
                    foodImage,
                    foodDescription,
                    foodIngridients,
                    foodItemQuantities
                )

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order making Failed try again!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodImage: MutableList<String>,
        foodDescription: MutableList<String>,
        foodIngridients: MutableList<String>,
        foodItemQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayoutActivity::class.java)
            intent.putExtra("foodName", foodName as ArrayList<String>)
            intent.putExtra("foodPrice", foodPrice as ArrayList<String>)
            intent.putExtra("foodImage", foodImage as ArrayList<String>)
            intent.putExtra("foodDescription", foodDescription as ArrayList<String>)
            intent.putExtra("foodQuantity", foodItemQuantities as ArrayList<Int>)
            intent.putExtra("foodIngridients", foodIngridients as ArrayList<String>)
            startActivity(intent)
        }

    }

    private fun retrieveItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("catItems")
//        list to stire itrems
        foodNames = mutableListOf()
        foodPrice = mutableListOf()
        foodImageUri = mutableListOf()
        foodDescription = mutableListOf()
        foodIngridients = mutableListOf()

        foodIngridients = mutableListOf()
        quantity = mutableListOf()

//        fetch ddata from the database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnashot in snapshot.children) {
//                    get the cart item  object form the child node
                    val cartItems = foodSnashot.getValue(Cart::class.java)

                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrice.add(it) }
                    cartItems?.foodImage?.let { foodImageUri.add(it) }
                    cartItems?.foodDescription?.let { foodDescription.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngridient?.let { foodIngridients.add(it) }
                }

                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data did not fetch", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAdapter() {
        cartAdapter = CartAdapter(
            requireContext(),
            foodNames,
            foodPrice,
            foodDescription,
            foodImageUri,
            quantity,
            foodIngridients
        )
        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartRecyclerView.adapter = cartAdapter
    }


}

