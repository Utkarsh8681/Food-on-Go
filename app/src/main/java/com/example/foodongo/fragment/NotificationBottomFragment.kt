package com.example.foodongo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.NotificationAdapter
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottomFragment : BottomSheetDialogFragment() {
private lateinit var binding:FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)
        val notificationName = listOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")
        val notificationIcon = listOf(R.drawable.sademoji,R.drawable.restorent,R.drawable.congratulation)
        val adapter = NotificationAdapter(
            ArrayList(notificationName),
            ArrayList(notificationIcon)
        )
binding.recyclerViewNotification.layoutManager= LinearLayoutManager(requireContext())
        binding.recyclerViewNotification.adapter = adapter

        return binding.root
    }


    companion object {

    }
}