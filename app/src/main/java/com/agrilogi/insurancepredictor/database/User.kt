package com.agrilogi.insurancepredictor.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
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

    @SerializedName("age")
    @ColumnInfo(name = "age")
    var age: String? = null,
    @SerializedName("sex")
    @ColumnInfo(name = "sex")
    var sex: String? = null,

    @ColumnInfo(name = "height")
    var height: Int? = null,
    @ColumnInfo(name = "weight")
    var weight: Int? = null,

    @SerializedName("bmi")
    @ColumnInfo(name = "bmi")
    var bmi: Float? = null,

    @SerializedName("smoker")
    @ColumnInfo(name = "smoke")
    var smoke: String? = null,

    @SerializedName("children")
    @ColumnInfo(name = "child")
    var child: String? = null,

    @SerializedName("region")
    @ColumnInfo(name = "location")
    var location: String? = null,


    @ColumnInfo(name = "price")
    var price: String? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
): Parcelable