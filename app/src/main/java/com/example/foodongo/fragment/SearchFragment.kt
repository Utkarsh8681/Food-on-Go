package com.example.foodongo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.MenuAdapter
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
 private lateinit var binding : FragmentSearchBinding
 private lateinit var adapter: MenuAdapter
 private val orignalMenu =listOf("sandwih","dgsfh","dvgdjgvfg d","hvjsd","dhvjsd","sandwih","dgsfh","dvgdjgvfg d","hvjsd","dhvjsd")
   private val orignalFoodPrice = listOf("$12","$12","$12","$12","$12","$12","$12","$12","$12","$12","$12","$12")
    private val orignalImage = listOf(
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
private val filteredMenuFoodName = mutableListOf<String>()
private val filteredMenuFoodPrice = mutableListOf<String>()
private val filteredMenuFoodImage = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
//        adapter  = MenuAdapter(filteredMenuFoodName,filteredMenuFoodPrice,filteredMenuFoodImage,requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter =adapter

        setupSearchView()
//        show all menu items
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuFoodName.clear()
        filteredMenuFoodPrice.clear()
        filteredMenuFoodImage.clear()


        filteredMenuFoodName.addAll(orignalMenu)
        filteredMenuFoodPrice.addAll(orignalFoodPrice)
        filteredMenuFoodImage.addAll(orignalImage)
        adapter.notifyDataSetChanged()
    }

     private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }


        })

    }

    private fun filterMenuItems(query: String?) {
         filteredMenuFoodName.clear()
        filteredMenuFoodPrice.clear()
        filteredMenuFoodImage.clear()
        orignalMenu.forEachIndexed { index, foodName ->
            if (foodName.contains(query.toString(),ignoreCase = true)){
                filteredMenuFoodName.add(foodName)
                filteredMenuFoodPrice.add(orignalFoodPrice[index])
                filteredMenuFoodImage.add(orignalImage[index])
            }
        }
adapter.notifyDataSetChanged()
    }



    companion object {

       }
}