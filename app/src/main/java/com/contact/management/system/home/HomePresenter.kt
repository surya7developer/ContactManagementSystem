package com.contact.management.system.home

import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.Contacts.People
import android.provider.ContactsContract
import com.contact.management.system.model.User
import com.contact.management.system.model.UserContact
import com.contact.management.system.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomePresenter(
    var view: HomeListener.View,
    val context: HomeActivity,
) : HomeListener.Presenter {

    private val DATA_COLS = arrayOf(
        ContactsContract.Data.MIMETYPE,
        ContactsContract.Data.DATA1,  //phone number
        ContactsContract.Data.CONTACT_ID
    )

    lateinit var cursorInfo: Cursor
    lateinit var cursor: Cursor

    override fun doLogout() {
     GlobalScope.launch {
            val database = context.getRoomObject()
            val user = database.DoaService().getUser()

            var updateUser = User(
                id = user.id,
                fName = user.fName,
                lName = user.lName,
                mobile = user.mobile,
                email = user.email,
                pass = user.pass,
                isLogin = 0
            )

            database.DoaService().updateUser(updateUser)
            view.onSuccess("Logout Successfully")
            view.onMoveLogin()
        }
    }

    override fun fetchData() {
        GlobalScope.launch {


            val database = context.getRoomObject()
            var contactList = database.DoaService().getUserContactListDatabase()
            if (contactList.isNotEmpty()){
                view.reLoad(contactList)
            }else {
                contactList = getContacts(context)
                context.getRoomObject().DoaService().insertContactList(contactList)
                view.reLoad(contactList)
            }



        }
    }

    override fun onUpdateContact(list: List<UserContact>, data: UserContact) {

        isLoaderShow.postValue( true)
        GlobalScope.launch {
            context.getRoomObject().DoaService().updateContactList(list)
            updateNameAndNumber(context,data.mobileNumber,data.firstName.plus(" ").plus(data.lastName))
        }
        isLoaderShow.postValue( false)
    }

    @SuppressLint("Range")
    fun getContacts(ctx: Context): List<UserContact> {


        isLoaderShow.postValue(true)
        val list: MutableList<UserContact> = ArrayList()
        val contentResolver: ContentResolver = ctx.contentResolver
        cursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)!!
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    cursorInfo = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )!!

                    while (cursorInfo.moveToNext()) {
                        var fName = ""
                        var lName = ""

                        val fullName : String = cursorInfo.getString(cursorInfo.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME))
                        val names = fullName.split("\\s".toRegex())

                        if (names.isNotEmpty() && names.size > 1){
                            fName = names[0]
                            lName = names[1]
                        } else if (names.isNotEmpty()){
                            fName = names[0]
                        }


                       // cNumber = cursorInfo.getString(cursorInfo.getColumnIndex("data1"));

                        val info = UserContact(
                            id = id.toInt(),
                            firstName = fName,
                            lastName = lName,
                            mobileNumber = cursorInfo.getString(
                                cursorInfo.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            ),
                            email = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)),
                        )
                        list.add(info)
                    }
                    cursorInfo.close()
                }
            }
            cursor.close()
        }
        isLoaderShow.postValue( false)
        return list
    }

    fun updateNameAndNumber(
        context: Context,
        number: String,
        newName: String,
    ) {
        val contactId = getContactId(context, number)

        //selection for name
        var where = java.lang.String.format(
            "%s = '%s' AND %s = ?",
            DATA_COLS.get(0),  //mimetype
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
            DATA_COLS.get(2) /*contactId*/
        )
        val args = arrayOf(contactId)
        val operations: ArrayList<ContentProviderOperation> = ArrayList()
        operations.add(
            ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, args)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, newName)
                .build()
        )

        //change selection for number
        where = java.lang.String.format(
            "%s = '%s' AND %s = ?",
            DATA_COLS[0],  //mimetype
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            DATA_COLS[1] /*number*/
        )

        //change args for number
        args[0] = number
        operations.add(
            ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, args)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, newName)
                .build()
        )
        try {
            val results = context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
            for (result in results) {
                context.showLog("Update Result", result.toString())
            }
            context.showToast("Contact update successfully")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @SuppressLint("Range")
    fun getContactId(context: Context?, number: String): String? {
        if (context == null) return null
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            ContactsContract.CommonDataKinds.Phone.NUMBER + "=?",
            arrayOf(number),
            null
        )
        if (cursor == null || cursor.count == 0) return null
        cursor.moveToFirst()
        val id =
            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
        cursor.close()
        return id
    }


}