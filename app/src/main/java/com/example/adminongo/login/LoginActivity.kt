package com.example.adminongo.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adminongo.MainActivity
import com.example.adminongo.R
import com.example.adminongo.databinding.ActivityLoginBinding
import com.example.adminongo.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {


private lateinit var auth: FirebaseAuth
//private lateinit var database: DatabaseReference
//private lateinit var googleSignInClient: GoogleSignInClient

//private  val  email: String?= null
//    private val password : String ?= null
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

auth = FirebaseAuth.getInstance()

//        database = Firebase.database.reference

        binding.dontHave.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginBtn.setOnClickListener {
            val email  = binding.LoginEmail.text.toString()
            val password = binding.LoginPassword.text.toString()

            if (email.isEmpty()||password.isEmpty()){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Login Succesfull ", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                        }
                    }
            }
        }



        }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    }



