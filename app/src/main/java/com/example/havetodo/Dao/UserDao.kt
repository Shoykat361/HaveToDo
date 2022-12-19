package com.example.havetodo.Dao

import androidx.room.*
import com.example.havetodo.entities.UserModel

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userModel: UserModel) : Long

    @Delete
    suspend fun deleteUser(userModel: UserModel)

    @Update
    suspend fun updateUser(userModel: UserModel)

    @Query("select * from tbl_user where email = :email")
    suspend fun getUserByEmail(email: String) : UserModel?

    @Query("select * from tbl_user where user_id = :userId")
    suspend fun getUserById(userId: Long) : UserModel
}