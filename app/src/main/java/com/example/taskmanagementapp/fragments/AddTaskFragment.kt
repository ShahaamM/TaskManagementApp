package com.example.taskmanagementapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.taskmanagementapp.MainActivity
import com.example.taskmanagementapp.R
import com.example.taskmanagementapp.databinding.FragmentAddTaskBinding
import com.example.taskmanagementapp.model.Task
import com.example.taskmanagementapp.viewmodel.TaskViewModel
import java.util.Calendar

class AddTaskFragment : Fragment(R.layout.fragment_add_task), MenuProvider {

    private var addTaskBinding: FragmentAddTaskBinding? = null
    private val binding get() = addTaskBinding!!

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var addTaskView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel
        addTaskView = view
    }

    private fun saveTask(view: View){
        val taskTitle = binding.addNoteTitle.text.toString().trim()
        val taskDesc = binding.addNoteDesc.text.toString().trim()
        val deadline = getSelectedDeadline(binding.addDeadline)
        val priority = getPriorityFromRadioGroup(binding.addPriorityRadioGroup)

        if(taskTitle.isNotEmpty()){
            val task = Task(0, taskTitle, taskDesc, deadline, priority)
            tasksViewModel.addTask(task)

            Toast.makeText(addTaskView.context, "Task Added", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        }else{
            Toast.makeText(addTaskView.context, "Please enter details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSelectedDeadline(datePicker: DatePicker): Long {
        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        return calendar.timeInMillis
    }

    private fun getPriorityFromRadioGroup(radioGroup: RadioGroup): String {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.highPriority -> "HIGH"
            R.id.mediumPriority -> "MEDIUM"
            R.id.lowPriority -> "LOW"
            else -> "LOW" // Default to LOW if no priority selected
        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveTask(addTaskView)
                return true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addTaskBinding = null
    }

}