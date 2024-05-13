package com.example.taskmanagementapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagementapp.databinding.TaskLayoutBinding
import com.example.taskmanagementapp.fragments.HomeFragment
import com.example.taskmanagementapp.fragments.HomeFragmentDirections
import com.example.taskmanagementapp.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val itemBinding: TaskLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    //DiffUtil checks if theres difference between two items
    private val differCallback = object : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.taskDesc == newItem.taskDesc &&
                    oldItem.taskTitle == newItem.taskTitle
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Inside onBindViewHolder() function of TaskAdapter
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = differ.currentList[position]

        holder.itemBinding.noteTitle.text = currentTask.taskTitle
        holder.itemBinding.noteDesc.text = currentTask.taskDesc
        holder.itemBinding.deadline.text = formatDeadline(currentTask.deadline!!) // Set deadline text
        holder.itemBinding.priority.text = currentTask.priority // Set priority text

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(currentTask)
            it.findNavController().navigate(direction)
        }
    }

    // Custom function to format deadline if needed
    private fun formatDeadline(deadline: Long): String {
        // Implement your custom formatting logic here if needed
        return SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date(deadline))
    }

}