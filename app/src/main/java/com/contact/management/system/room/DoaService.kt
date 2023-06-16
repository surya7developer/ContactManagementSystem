package com.contact.management.system.room

import androidx.room.*
import com.contact.management.system.model.User
import com.contact.management.system.model.UserContact


@Dao
interface DoaService {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsrContactList(userDataList: List<UserContact>)

    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM UserContact")
    fun getUserContactListDatabase() : List<UserContact>

    @Query("SELECT * FROM User")
    fun getUser() : User

    @Update
    fun updateUser(user: User)

    @Update
    fun updateContactList(userContactList: List<UserContact>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactList(userContactList: List<UserContact>)

}