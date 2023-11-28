package com.example.adminongo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminongo.cards.AddItemsActivity
import com.example.adminongo.cards.AdminProfileActivity
import com.example.adminongo.cards.AllItemActivity
import com.example.adminongo.cards.CreateNewProfileActivity
import com.example.adminongo.cards.OutForDeliveryActivity
import com.example.adminongo.cards.PendingOrderActivity
import com.example.adminongo.databinding.ActivityMainBinding
import com.example.adminongo.login.LoginActivity

class MainActivity : AppCompatActivity() {
     private val binding : ActivityMainBinding by lazy {
         ActivityMainBinding.inflate(layoutInflater)
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addMenu.setOnClickListener {
            val intent = Intent(this, AddItemsActivity::class.java)
            startActivity(intent)

        }
        binding.AllItemMenu.setOnClickListener {
            val intent = Intent(this, AllItemActivity::class.java)
            startActivity(intent)

        }

        binding.profileActivity.setOnClickListener {
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)

        }
        binding.newUser.setOnClickListener {
            val intent = Intent(this, CreateNewProfileActivity::class.java)
            startActivity(intent)

        }
        binding.orderDispatch.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)

        }
        binding.logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)


        }

           binding.pendingText.setOnClickListener {
                val intent = Intent(this,PendingOrderActivity::class.java)
                startActivity(intent)


        }
        binding.orderDispatch.setOnClickListener{
            val intent = Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)

        }
    }


}