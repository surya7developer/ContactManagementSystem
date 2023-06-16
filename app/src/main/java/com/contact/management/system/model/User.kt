package com.contact.management.system.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val fName : String,
    val lName : String,
    val mobile : String,
    val email : String,
    val pass : String,
    var isLogin : Int
)
