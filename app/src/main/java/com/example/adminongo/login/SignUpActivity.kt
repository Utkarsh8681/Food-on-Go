package com.example.adminongo.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.adminongo.R
import com.example.adminongo.databinding.ActivityLoginBinding
import com.example.adminongo.databinding.ActivitySignUpBinding
import com.example.adminongo.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

//import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private  val name: String?= null
    private lateinit var database: DatabaseReference
    private val  password: String?= null
    private val  email: String?= null
    private val restaurentName: String?= null


    private val binding : ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        initialize firebase auth

        auth = Firebase.auth
        database =Firebase.database.reference

        binding.SignUpBtn.setOnClickListener {


            val user = binding.useName.text.toString().trim()
            val  email = binding.emailorphone.text.toString().trim()
            val password = binding.signPassword.text.toString().trim()
            val restaurentName = binding.restaurentName.text.toString().trim()

            if (user.isBlank() || restaurentName.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Enter All text Fields", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }


        }
        binding.AlreadyHave.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        
    }

    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
//                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                saveAdminData()
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Account creation failed:${task.exception?.message}", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun saveAdminData() {
        val userName = binding.useName.text.toString()
        val Reataurent_Name = binding.restaurentName.text.toString()
        val email = binding.emailorphone.text.toString()
        val password = binding.signPassword.text.toString()

        val admin = UserModel(userName,Reataurent_Name,email,password)
        val userId =FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("admin").child(userId).setValue(admin)
        }
    }
//save fata into data base
//    private fun saveUserData() {
//
//        user = binding.useName.text.toString().trim()
//        email = binding.emailorphone.text.toString().trim()
//        password = binding.signPassword.text.toString().trim()
//        restaurentName = binding.restaurentName.text.toString().trim()
//        val costumer = UserModel(user,restaurentName,email,password)
//
//        val userId = FirebaseAuth.getInstance().currentUser!!.uid
////save data into firebases
//        database.child("costumer").child(userId).setValue(costumer)
//    }
}