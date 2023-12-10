package com.example.adminongo.cards

import android.media.AudioMetadata
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.adminongo.databinding.ActivityAddItemsBinding
import com.example.adminongo.model.addItems
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import java.security.Key

class AddItemsActivity : AppCompatActivity() {
    private val binding: ActivityAddItemsBinding by lazy {
        ActivityAddItemsBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private var foodImageUri: Uri? = null
    private lateinit var description: String
    private lateinit var ingridients: String

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//iinitializing firebase database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.addFoodImage.setOnClickListener {
            pickImage.launch("image/*")

        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.addedImage.setImageURI(uri)
            foodImageUri = uri
        }
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.addItem.setOnClickListener {
            foodName = binding.addFoodname.text.toString()
            foodPrice = binding.addFoodPrice.text.toString()
            description = binding.shortDescription.text.toString()
            ingridients = binding.Ingridients.text.toString()
            if (!(foodName.isBlank() || foodPrice.isBlank() || ingridients.isBlank() || description.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item added Sucessfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill all Fields", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun uploadData() {

        val menuRef: DatabaseReference = database.getReference("menu")

//        id
        val newItemKey = menuRef.push().key
        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->

                    val newItem = addItems(
                        newItemKey,
                        foodName = foodName,
                        foodPrice = foodPrice,
                        description = description,
                        ingridients = ingridients,
                        foodImage = downloadUrl.toString(),
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Data added sucessfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "dat uload Failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }
                .addOnFailureListener {
                    Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
                }


        } else {
            Toast.makeText(this, "Please Select a Image", Toast.LENGTH_SHORT).show()

        }
    }
}