package com.example.moveitpartner.ui.home.bulletin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.moveitpartner.databinding.FragmentWelcomeBulletinBinding
import com.example.moveitpartner.model.viewmodel.HomeViewModel

class WelcomeBulletinFragment : Fragment() {

    private var _binding: FragmentWelcomeBulletinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBulletinBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        if (binding.textHome.text.isEmpty()) {
            model.setWelcomeTxt(binding.textHome)
        }
    }

}