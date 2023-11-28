package com.example.foodongo.Login

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodongo.FrontActivity
import com.example.foodongo.MainActivity
import com.example.foodongo.Model.UserModel
import com.example.foodongo.R
import com.example.foodongo.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference

import com.google.firebase.auth.auth
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val name: String? = null
    private val email: String? = null
    private val password: String? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.login.setOnClickListener {
           val email = binding.loginEmail.text.toString()
            val password = binding.loginPass.text.toString()
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,FrontActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this, "Login Fields : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                        
                    }
            }
binding.Goglebutton.setOnClickListener {
    val signInIntent = googleSignInClient.signInIntent
    launcher.launch(signInIntent)

}

        }
        binding.dontAccount.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
    if (result.resultCode == Activity.RESULT_OK) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            val credentials = GoogleAuthProvider.getCredential(account?.idToken, null)
            auth.signInWithCredential(credentials).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, FrontActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Sign In failed!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    } else {
        Toast.makeText(this, "SignIn Failed!", Toast.LENGTH_SHORT).show()
    }
}

  }