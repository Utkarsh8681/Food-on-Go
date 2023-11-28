package com.example.foodongo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.MenuAdapter
import com.example.foodongo.Model.MenuItem
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {
    private lateinit var  binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

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
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.viewMenu.setOnClickListener {
            val bottomSheetDialog = BottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"test")
        }
        retrieveAndDisplayPopularItem()
        return binding.root

    }

    private fun retrieveAndDisplayPopularItem() {
        database = FirebaseDatabase.getInstance()

        val foodRef:DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

//        retrieve
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }

                    randomPopularItem()
                }

            }

            private fun randomPopularItem() {
//                creat shiflr
                val index = menuItems.indices.toList().shuffled()
                val numItem = 6
                val subsetMenuItem = index.take(numItem).map { menuItems[it] }

                setPopularItemsAdapter(subsetMenuItem)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun setPopularItemsAdapter(subsetMenuItem: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItem,requireContext())
        binding.popularRecyclerView.layoutManager =LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val imageList  = ArrayList<SlideModel>()
//        imageList.add(SlideModel(R.drawable.banner1, ScaleType.FIT_CENTER))
//        imageList.add(SlideModel(R.drawable.banner1, ScaleType.FIT_CENTER))
//        imageList.add(SlideModel(R.drawable.banner1, ScaleType.FIT_CENTER))
//        val imageSlide= binding.i

    }


}