package com.contact.management.system.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserContact")
data class UserContact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var firstName: String,
    var lastName: String,
    val mobileNumber: String,
    var email: String,
)
