package com.agrilogi.insurancepredictor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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

    @Query("UPDATE users SET age = :age, sex = :sex WHERE email = :email")
    fun updateAgeSex(age: String, sex: String, email: String)

    @Query("UPDATE users SET height = :height, weight = :weight WHERE email = :email")
    fun updateBMI(height: Int, weight: Int, email: String)

    @Query("UPDATE users SET child = :child, location = :location WHERE email = :email")
    fun updateChill(child: Int, location: String, email: String)

    @Update
    fun updateAgeSex(vararg user:User)

    @Update
    fun updateBMI(vararg user:User)

    @Update
    fun updateChill(vararg user:User)

    @Query("SELECT * from users")
    fun getAll(): List<User>
}