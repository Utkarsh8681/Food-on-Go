package com.example.foodongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodongo.databinding.ActivityFrontBinding
import com.example.foodongo.databinding.FragmentNotificationBottomBinding
import com.example.foodongo.fragment.NotificationBottomFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FrontActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFrontBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var navController = findNavController(R.id.fragmentContainerView)
        var bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)
        binding.NotificationBtn.setOnClickListener {
            val bottomSheetDialog = NotificationBottomFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")

        }
    }
}