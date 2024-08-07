package com.example.taskmanagementapp.repository

import androidx.room.Query
import com.example.taskmanagementapp.database.TaskDatabase
import com.example.taskmanagementapp.model.Task

class TaskRepository(private val db: TaskDatabase) {

    //insert, delete, search, view and update funcitons implemented
    suspend fun insertTask(task: Task) = db.getTaskDao().insertTask(task)
    suspend fun deleteTask(task: Task) = db.getTaskDao().deleteTask(task)
    suspend fun updateTask(task: Task) = db.getTaskDao().updateTask(task)

    fun getAllTasks() = db.getTaskDao().getAllTasks()

    fun getAllTasksSortedByPriority() = db.getTaskDao().getAllTasksSortedByPriority()

    fun searchTask(query: String?) = db.getTaskDao().searchTasks(query)

}