package com.example.havetodo.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.havetodo.Db.TodoDatabase
import com.example.havetodo.entities.TodoModel
import com.example.havetodo.repos.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDB(application).todoDao()
    private val repository = TodoRepository(todoDao)
    var todoList = listOf<TodoModel>()
    val idLD: MutableLiveData<Long> = MutableLiveData()
    fun insertTodo(todoModel: TodoModel) {
        viewModelScope.launch {
            idLD.value = repository.insertTodo(todoModel)
        }
    }

    fun getTodoByUserId(userId: Long) = repository.getTodoByUserId(userId)

    fun getTodoByStatusUserId(userId: Long, status: Int) =
        repository.getTodoByStatusUserId(userId, status)

    fun getTodosByPriorityAndUserId(userId: Long, priority: String) =
        repository.getTodosByPriorityAndUserId(userId, priority)


    fun deleteTodo(todoModel: TodoModel) {
        viewModelScope.launch {
            repository.deleteTodo(todoModel)
        }
    }

    fun getTODoById(id: Long): LiveData<TodoModel> = repository.getTODoById(id)


    fun updateTodo(todoModel: TodoModel) {
        viewModelScope.launch {
            repository.updateTodo(todoModel)
        }
    }



}
