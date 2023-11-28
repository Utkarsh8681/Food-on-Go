package com.example.foodongo.Login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodongo.FrontActivity
import com.example.foodongo.MainActivity
import com.example.foodongo.Model.UserModel
import com.example.foodongo.R
import com.example.foodongo.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val name: String? = null
    private val email: String? = null
    private val password: String? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        binding.signup.setOnClickListener {
            val email = binding.signinEmail.text.toString()
            val name = binding.SigninName.text.toString()
            val password = binding.siigninPass.text.toString()
            if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all the Fields", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Sigin in complete", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            saveUserData()
                        } else {
                            Toast.makeText(
                                this,
                                "Sign in  Failed : ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
            }
        }
        binding.Googlebutton.setOnClickListener {
            val signInntent = googleSignInClient.signInIntent
            launcher.launch(signInntent)

        }
        binding.alreadyAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    private fun saveUserData() {
        val name = binding.SigninName.text.toString()
        val email = binding.signinEmail.text.toString()
        val password = binding.siigninPass.text.toString()

        val user = UserModel(name, email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(userId).setValue(user)
        }

    }
}