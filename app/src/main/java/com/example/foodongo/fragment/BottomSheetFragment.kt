package com.example.foodongo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.MenuAdapter
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var  binding:FragmentBottomSheetBinding


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
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)

        binding.backBtn.setOnClickListener {
            dismiss()
        }
        val menuFoodName = listOf("sandwih","dgsfh","dvgdjgvfg d","hvjsd","dhvjsd","sandwih","dgsfh","dvgdjgvfg d","hvjsd","dhvjsd")
        val menuFoodPrice = listOf("$12","$12","$12","$12","$12","$12","$12","$12","$12","$12","$12","$12")
        val menuImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
        )
        val adapter  = MenuAdapter(ArrayList(menuFoodName),ArrayList(menuFoodPrice),ArrayList(menuImage),requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}