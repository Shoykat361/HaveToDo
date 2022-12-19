package com.example.havetodo.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.havetodo.Dao.UserDao
import com.example.havetodo.Db.TodoDatabase
import com.example.havetodo.entities.UserModel
import kotlinx.coroutines.launch

/*
class LoginViewModel(application: Application)
    : AndroidViewModel(application) {
    private val userDao = TodoDatabase.getDB(application).userDao()
    val errMsgLD: MutableLiveData<String> = MutableLiveData()
    var userModel: UserModel? = null
    fun getUserById(userId : Long){
        viewModelScope.launch {
            userModel=userDao.getUserById(userId)

        }

    }



    fun login(email: String, passwowrd: String, callback:(Long) -> Unit) {
        viewModelScope.launch {
            userModel = userDao.getUserByEmail(email)
            if (userModel != null) {
                if (userModel!!.password == passwowrd) {
                    callback(userModel!!.userId)
                } else {
                    errMsgLD.value = "Incorrect password"
                }
            } else {
                errMsgLD.value = "Email does not exist"
            }
        }
    }

    fun register(user: UserModel, callback:(Long) -> Unit) {
        viewModelScope.launch {
            userModel = userDao.getUserByEmail(user.email)
            if (userModel != null) {
                errMsgLD.value = "Email already exists"
            }else {
                val rowid = userDao.insertUser(user)
                userModel = user.apply {
                    userId = rowid
                }
                callback(rowid)
            }
        }
    }
}*/
class
LoginViewModel(application: Application)
    : AndroidViewModel(application){
    private var userDao: UserDao =
        TodoDatabase.getDB(application).userDao()

    val errMsgLD: MutableLiveData<String> = MutableLiveData()
    var userModel: UserModel? = null

    fun getUserById(userId : Long){
        viewModelScope.launch {
            userModel=userDao.getUserById(userId)

        }

    }

    fun login(email: String, password: String, callback: (Long) -> Unit) {
        viewModelScope.launch {
            userModel = userDao.getUserByEmail(email)
            if (userModel != null) {
                if (password == userModel!!.password) {
                    callback(userModel!!.userId)
                } else {
                    errMsgLD.value = "Incorrect password"
                }
            }else {
                errMsgLD.value = "Email does not exist"
            }
        }
    }

    fun register(user: UserModel, callback: (Long) -> Unit) {
        viewModelScope.launch {
            userModel = userDao.getUserByEmail(user.email)
            if (userModel != null) {
                errMsgLD.value = "Email already exists!!"
            } else {
                val id = userDao.insertUser(user)
                user.userId = id
                userModel = user
                callback(id)
            }

        }
    }
}
