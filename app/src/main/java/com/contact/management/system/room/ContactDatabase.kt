package com.contact.management.system.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.contact.management.system.model.User
import com.contact.management.system.model.UserContact

@Database(entities = [UserContact::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun DoaService(): DoaService

    companion object {

        var dbObject: ContactDatabase? = null
        fun getDatabase(context: Context): ContactDatabase {
            synchronized(this) {
                if (dbObject == null) {
                    dbObject = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "userContactListDB"
                    )
                        .build()
                }
            }
            return dbObject!!
        }
    }

}