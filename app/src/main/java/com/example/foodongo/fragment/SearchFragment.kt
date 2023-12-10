package com.example.foodongo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
//import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.foodongo.Model.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.MenuAdapter
import com.example.foodongo.databinding.FragmentSearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItem = mutableListOf<MenuItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

//        retrieve menu item from database
        retrieveMenuItem()



        setupSearchView()

        return binding.root
    }

    private fun retrieveMenuItem() {
//get data base reference
        database = FirebaseDatabase.getInstance()
//        reference to the menu node
        val foodReference: DatabaseReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        originalMenuItem.add(it)
                    }
                }
                showAllMenus()

            }



            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    private fun showAllMenus() {

        val filteredMenuItem = ArrayList(originalMenuItem)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {

        adapter = MenuAdapter( filteredMenuItem , requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter= adapter
    }





    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        val filteredMenuItems = originalMenuItem.filter {
            it.foodName?.contains(query!!, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItems)
    }
  companion object{

  }
}
