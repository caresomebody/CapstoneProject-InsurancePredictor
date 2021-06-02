package com.agrilogi.insurancepredictor.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(

    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "age")
    var age: String? = null,
    @ColumnInfo(name = "sex")
    var sex: String? = null,
    @ColumnInfo(name = "height")
    var height: Int? = null,
    @ColumnInfo(name = "weight")
    var weight: Int? = null,
    @ColumnInfo(name = "smoke")
    var smoke: String? = null,
    @ColumnInfo(name = "child")
    var child: String? = null,
    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "bmi")
    var bmi: Int? = null,
    @ColumnInfo(name = "price")
    var price: Int? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
): Parcelable