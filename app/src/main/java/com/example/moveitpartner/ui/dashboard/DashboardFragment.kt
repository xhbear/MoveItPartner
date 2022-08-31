package com.example.moveitpartner.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.moveitpartner.databinding.FragmentDashboardBinding
import com.example.moveitpartner.databinding.WorkoutlogLayoutBinding
import com.example.moveitpartner.model.data.WorkoutLog
import com.example.moveitpartner.model.viewmodel.HomeViewModel
import java.time.format.DateTimeFormatter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.workoutLogRecyclerView.layoutManager = layoutManager
        val adapter = DashboardAdapter()
        binding.workoutLogRecyclerView.adapter = adapter
        viewModel.dao.getAll().observe(viewLifecycleOwner, Observer { newWorkoutLog ->
            adapter.submitList(newWorkoutLog)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class DashboardAdapter : ListAdapter<WorkoutLog, DashboardAdapter.ViewHolder>(WorkoutLogDiffCallback) {

        inner class ViewHolder(binding: WorkoutlogLayoutBinding): RecyclerView.ViewHolder(binding.root) {
            val startTime: TextView = binding.startTime
            val endTime: TextView = binding.endTime
            val duration: TextView = binding.duration
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = WorkoutlogLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val log = getItem(position)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            holder.startTime.text = log.startTime.format(formatter)
            holder.endTime.text = log.endTime.format(formatter)
            holder.duration.text = log.duration.toString()
        }
    }
    object WorkoutLogDiffCallback: DiffUtil.ItemCallback<WorkoutLog>() {
        override fun areItemsTheSame(oldItem: WorkoutLog, newItem: WorkoutLog): Boolean {
            return oldItem.logId == newItem.logId
        }

        override fun areContentsTheSame(oldItem: WorkoutLog, newItem: WorkoutLog): Boolean {
            return oldItem == newItem
        }
    }
}