package com.example.moveitpartner.ui.home.bulletin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moveitpartner.databinding.FragmentSadkittyBulletinBinding

class SadKittyBulletinFragment : Fragment() {

    private var _binding: FragmentSadkittyBulletinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSadkittyBulletinBinding.inflate(inflater, container, false)
        return binding.root
    }
}