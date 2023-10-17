package com.example.foodongo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.CartAdapter
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentCartBinding
import com.example.foodongo.databinding.FragmentHomeBinding
//import com.example.foodongo.fragment.layoutManager as layoutManager

//private val ConstraintLayout.layoutManager: Any
//    get() {}

class CartFragment : Fragment() {
 private lateinit var binding: FragmentCartBinding
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
        binding = FragmentCartBinding.inflate(inflater,container,false)
        val cartFoodName = listOf("sandwih","dgsfh","dvgdjgvfg d","hvjsd","dhvjsd")
        val cartFoodPrice = listOf("$12","$12","$12","$12","$12","$12")
        val cartImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
        )
        val adapter  = CartAdapter(ArrayList(cartFoodName),ArrayList(cartFoodPrice),ArrayList(cartImage))
         binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

          return binding.root
    }

    companion object {


    }
}