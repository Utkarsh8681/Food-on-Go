package com.example.foodongo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodongo.databinding.ActivityPayoutBinding
import com.example.foodongo.databinding.FragmentCongratulationsBinding
import com.example.foodongo.fragment.BottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class congratulationsFragment : BottomSheetDialogFragment() {

private lateinit var binding: FragmentCongratulationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       binding = FragmentCongratulationsBinding.inflate(inflater,container,false)
        binding.gohome.setOnClickListener {
            val intent = Intent(requireContext(),FrontActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {

    }
}