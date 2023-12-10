package com.example.adminongo.cards

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminongo.adapter.MenuItemAdapter
import com.example.adminongo.databinding.ActivityAllItemBinding
import com.example.adminongo.model.addItems
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference :DatabaseReference
    private lateinit var database: FirebaseDatabase
    private  var menuItems: ArrayList<addItems> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()



       }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
//        menuItems = mutableListOf()

//        FETCH DATA FROM DATA BASE
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItems.clear()
//                loop for through each iterm
                for(foodSnapshot in snapshot.children){
                    val menuItem  = foodSnapshot.getValue(addItems::class.java)
                    menuItem?.let { menuItems.add(it)  }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {

                Log.d("DatabaseError", "Error : ${error.message}")
            }

        })
    }

    private fun setAdapter() {
        val adapter = MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference){position ->
            deleteMenuItem(position)
        }
        binding.AllItemeRecycle.layoutManager = LinearLayoutManager(this)
        binding.AllItemeRecycle.adapter = adapter

    }

    private fun deleteMenuItem(position: Int) {

        val menuItemToDelete = menuItems[position]
        val menuItemKey = menuItemToDelete.key

        val foodMenuRefernce = database.reference.child("menu").child(menuItemKey!!)
        foodMenuRefernce.removeValue().addOnCompleteListener {task ->
            if(task.isSuccessful){
                menuItems.removeAt(position)
                binding.AllItemeRecycle.adapter?.notifyItemRemoved(position)

            }
            else{
                Toast.makeText(this, "Item Not Removed", Toast.LENGTH_SHORT).show()
            }

        }
    }
}