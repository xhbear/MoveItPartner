package com.example.moveitpartner.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.moveitpartner.databinding.FragmentHomeBinding
import com.example.moveitpartner.ui.home.bulletin.SadKittyBulletinFragment
import com.example.moveitpartner.ui.home.bulletin.WelcomeBulletinFragment
import com.example.moveitpartner.model.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

private const val BULLETIN_PAGES = 2

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var model: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        model = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        viewPager = binding.welcomeBulletin

        val pagerAdapter = BulletinAdapter(this)
        viewPager.adapter = pagerAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logTime.setOnClickListener {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            model.viewModelScope.launch {
                if (model.clockCounter.value == 0) {
                    model.setClockInTime()
                    val time = model.clockInTime.value!!.format(formatter)
                    Toast.makeText(context, "CLOCKED-IN at $time", Toast.LENGTH_SHORT).show()
                } else {
                    model.setClockOutTime()
                    val time = model.clockOutTime.value!!.format(formatter)
                    Toast.makeText(context, "CLOCKED-OUT at $time", Toast.LENGTH_SHORT).show()
                    model.resetTimeValues()
                }
            }
        }
        model.clockCounter.observe(viewLifecycleOwner, Observer { newCounterValue ->
            model.displaySessionRunning(binding.sessionRunningMsg, newCounterValue)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class BulletinAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = BULLETIN_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    WelcomeBulletinFragment()
                }
                else -> {
                    SadKittyBulletinFragment()
                }
            }
        }
    }
}