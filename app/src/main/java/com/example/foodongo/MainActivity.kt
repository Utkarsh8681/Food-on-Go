package com.example.foodongo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodongo.Login.LoginActivity
import com.example.foodongo.Login.SignUpActivity
import com.example.foodongo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    binding.next.setOnClickListener {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
    }
}