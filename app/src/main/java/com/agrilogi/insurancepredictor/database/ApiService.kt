package com.agrilogi.insurancepredictor.database

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class ApiService {
//    fun addUser(user: User, onResult: (User?) -> Unit){
//        val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
//        retrofit.getCharge(user).enqueue(
//            object : Callback<User> {
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    onResult(null)
//                }
//                override fun onResponse( call: Call<User>, response: Response<User>) {
//                    val addedUser = response.body()
//                    onResult(addedUser)
//                }
//            }
//        )
//    }
//}