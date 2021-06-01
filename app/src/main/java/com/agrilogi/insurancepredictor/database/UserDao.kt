package com.agrilogi.insurancepredictor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM users where email = :email")
    fun checkUserEmail(email: String) : Boolean

    @Query("SELECT EXISTS( SELECT 1 FROM users where email = :email and password = :password)")
    fun checkUser(email: String,password: String) : Boolean

    @Query("SELECT * from users where email = :email")
    fun getUserByEmail(email: String) : User

    @Insert
    fun addUser(vararg user:User)

    @Query("SELECT * from users")
    fun getAll(): List<User>
}