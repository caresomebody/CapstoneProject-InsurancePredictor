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

    @Query("UPDATE users SET age = :age WHERE email = :email")
    fun updateAge(age: String, email: String)

    @Query("UPDATE users SET sex = :sex WHERE email = :email")
    fun updateSex(sex: String, email: String)

    @Query("UPDATE users SET height = :height, weight = :weight, bmi = :bmi WHERE email = :email")
    fun updateBMI(height: String, weight: String, bmi: Float, email: String)

    @Query("UPDATE users SET child = :child WHERE email = :email")
    fun updateChild(child: String, email: String)

    @Query("UPDATE users SET location = :location WHERE email = :email")
    fun updateLocation(location: String, email: String)

    @Query("UPDATE users SET smoke = :smoke WHERE email = :email")
    fun updateSmoke(smoke: String, email: String)

    @Query("UPDATE users SET price = :price WHERE email = :email")
    fun updateCharge(price: String, email: String)

    @Update
    fun updateAgeSex(vararg user:User)

    @Update
    fun updateBMI(vararg user:User)

    @Update
    fun updateSmoke(vararg user:User)

    @Update
    fun updateChill(vararg user:User)

    @Update
    fun updateCharge(vararg user:User)

    @Query("SELECT * from users")
    fun getAll(): List<User>
}