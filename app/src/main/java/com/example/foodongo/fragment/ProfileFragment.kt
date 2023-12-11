package com.example.foodongo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodongo.Model.UserModel
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment


        binding.saveInfo.setOnClickListener {
            val name = binding.name.text.toString()
            val address = binding.address.text.toString()
            val email = binding.email.text.toString()
            val phone = binding.phone.text.toString()

            updateUserData(name, address, email, phone)
        }

        setUserData()
        binding.apply {
            name.isEnabled = false
            email.isEnabled = false
            phone.isEnabled = false
            address.isEnabled = false
        }

        binding.editProfile.setOnClickListener {
            binding.apply {
                name.isEnabled = true
                email.isEnabled = true
                phone.isEnabled = true
                address.isEnabled = true
            }
        }


        return binding.root
    }

    private fun updateUserData(name: String, address: String, email: String, phone: String) {

        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "phone" to phone,
                "address" to address
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "profile added sucessfully", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "profile not added sucessfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun setUserData() {

        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null) {
                            binding.name.setText(userProfile.name)
                            binding.address.setText(userProfile.address)
                            binding.email.setText(userProfile.email)
                            binding.phone.setText(userProfile.phone)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    companion object {

    }
}