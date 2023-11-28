package com.example.foodongo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView.ScaleType
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodongo.Adapter.PopularAdapter
import com.example.foodongo.R
import com.example.foodongo.databinding.FragmentHomeBinding


@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {
    private lateinit var  binding: FragmentHomeBinding
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


        val foodName  = listOf("burgar","flies","momoz","pixxa")
        val PopularPrice = listOf("$5","$7","$1","$10")
        val imagePopular = listOf(R.drawable.menu1,R.drawable.menu4,R.drawable.menu3,R.drawable.menu2)

        val adapter =PopularAdapter(ArrayList(foodName) ,ArrayList(PopularPrice), ArrayList(imagePopular),requireContext())
        binding.popularRecyclerView.layoutManager =LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
        binding.viewMenu.setOnClickListener {
            val bottomSheetDialog = BottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"test")
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val imageList  = ArrayList<SlideModel>()
//        imageList.add(SlideModel(R.drawable.banner1, ScaleType.FIT_CENTER))
//        imageList.add(SlideModel(R.drawable.banner1, ScaleType.FIT_CENTER))
//        imageList.add(SlideModel(R.drawable.banner1, ScaleType.FIT_CENTER))
//        val imageSlide= binding.i
    }

    companion object {

    }
}