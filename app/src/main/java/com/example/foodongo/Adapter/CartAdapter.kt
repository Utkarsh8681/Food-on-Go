package com.example.foodongo.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodongo.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(
    private val context: Context,
    private val CartItems: MutableList<String>,
    private val CartItemPrice: MutableList<String>,
    private val CartImage: MutableList<String>,
    private val CartDescription : MutableList<String>,
    private val CartQuantity : MutableList<Int>,
    private val CartIngridients : MutableList<String>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val auth = FirebaseAuth.getInstance()
    init {
        val database = FirebaseDatabase.getInstance()
        val userId=   auth.currentUser?.uid?:""
        val cartItemNum = CartItems.size

        itemQuantities = IntArray(cartItemNum){1}
        cartItemRef = database.reference.child("user").child(userId).child("catItems")
    }
companion object{
    private var itemQuantities : IntArray = intArrayOf()
    private lateinit var cartItemRef :DatabaseReference
}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CartItems.size
    fun getUpdatedItemsQuantitiy(): MutableList<Int> {

        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(CartQuantity)
        return itemQuantity
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                itemName.text = CartItems[position]
                itemPrice.text = CartItemPrice[position]

//                load image
               val uriString = CartImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImage)
                itemCount.text = quantity.toString()
                minusBtn.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusbtn.setOnClickListener {
                    increaseQuantity(position)
                }
                trashbtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        delete(position)
                    }
                    delete(position)
                }


            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.itemCount.text = itemQuantities[position].toString()
            }

        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.itemCount.text = itemQuantities[position].toString()
            }

        }

        private fun delete(position: Int) {
            val posititionRetrieve = position
            getUniqueKeyPosition(posititionRetrieve){uniqueKey ->
                if (uniqueKey != null){
                    removeItem(position,uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if(uniqueKey != null){
                cartItemRef.child(uniqueKey).removeValue().addOnSuccessListener {
                    CartItems.removeAt(position)
                    CartImage.removeAt(position)
                    CartDescription.removeAt(position)
                    CartQuantity.removeAt(position)
                    CartItemPrice.removeAt(position)
                    CartIngridients.removeAt(position)
                    Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show()
//                    update item duantity
                    itemQuantities = itemQuantities.filterIndexed { index, i -> index != position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,CartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to delete!", Toast.LENGTH_SHORT).show()
                }
            }

        }

        private fun getUniqueKeyPosition(posititionRetrieve: Int , onComplete :(String?) -> Unit){
            cartItemRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqiueKey : String? =null
//                    loop gor snapshot children
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == posititionRetrieve){
                            uniqiueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqiueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
    }
}