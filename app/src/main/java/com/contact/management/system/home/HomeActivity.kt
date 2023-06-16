package com.contact.management.system.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.contact.management.system.databinding.ActivityHomeBinding
import com.contact.management.system.home.adapter.UserUserContactListAdapter
import com.contact.management.system.model.UserContact
import com.contact.management.system.room.ContactDatabase
import com.contact.management.system.util.*


class HomeActivity : AppCompatActivity(), HomeListener.View {

    private lateinit var presenter: HomeListener.Presenter
    private lateinit var database: ContactDatabase
    private lateinit var contactAdapter: UserUserContactListAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inilaization()
        initilizeAdapter()
        checkContactPermission()
        eventManagement()
        observar()
    }

    fun observar(){

        isLoaderShow.observe(this, Observer {
            if (binding!=null && it!=null && it)
                binding.progressBar.show()
            else
                binding.progressBar.hide()
        })

    }


    private fun inilaization() {
        presenter = HomePresenter(this,this@HomeActivity)
        database = getRoomObject()
    }

    private fun eventManagement() {
        binding.apply {
            btnLogout.setOnClickListener {
                presenter.doLogout()
            }
        }
    }

    private fun checkContactPermission() {

        val PERMISSIONS = arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS)
        if (!hasPermissions( PERMISSIONS)) {
            ActivityCompat.requestPermissions((this), PERMISSIONS, REQUEST)
        } else {
           presenter.fetchData()
        }
    }


    private fun hasPermissions(PERMISSIONS: Array<String>): Boolean {
        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }


    private fun initilizeAdapter() {

        var list = ArrayList<UserContact>()
        contactAdapter = UserUserContactListAdapter(list,this)
        binding.rvContactList.adapter = contactAdapter

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.fetchData()
                } else {
                    showLog(MAIN_ACTIVITY_LOG_TAG,"Permission Deny")
                    showToast("Permission Deny")
                }
                return
            }
        }
    }

    fun updateList(list: List<UserContact>, data: UserContact) {
        presenter.onUpdateContact(list,data)
    }

    override fun onSuccess(message: String) {
            showToast(message)
    }

    override fun onMoveLogin() {
        goToLogin()
        finish()
    }

    override fun reLoad(list: List<UserContact>) {
        runOnUiThread {
            contactAdapter.reaload(list) }
    }

}