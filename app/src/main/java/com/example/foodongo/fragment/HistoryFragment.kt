package com.example.foodongo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.BuyAgainAdapter
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

private lateinit var binding:FragmentHistoryBinding
private lateinit var buyAgainAdapter: BuyAgainAdapter
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
        binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        setUpRecyclerView()
        return binding.root
    }
 private fun setUpRecyclerView(){
     val buyAgainFood = arrayListOf("food1","food2","food2")
     val buyAgainPrice = arrayListOf("$5","$10","$12")
     val buyAgainImage = arrayListOf(
         R.drawable.menu1,
         R.drawable.menu2,
         R.drawable.menu3,
         )
     buyAgainAdapter = BuyAgainAdapter(buyAgainFood,buyAgainPrice,buyAgainImage)
     binding.historyRecycle.adapter =buyAgainAdapter
     binding.historyRecycle.layoutManager = LinearLayoutManager(requireContext())
 }
    companion object {


    }
}