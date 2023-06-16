package com.contact.management.system.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.contact.management.system.databinding.UserContactListItemBinding
import com.contact.management.system.home.HomeActivity
import com.contact.management.system.model.UserContact
import com.contact.management.system.util.*
import com.google.gson.Gson

class UserUserContactListAdapter(var list: List<UserContact>, val homeActivity: HomeActivity) :
    RecyclerView.Adapter<UserUserContactListAdapter.ViewHolder>() {

    private var selectedID = -1
    lateinit var binding: UserContactListItemBinding

    inner class ViewHolder(binding: UserContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            UserContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        binding.apply {

            var data = list[position]



            if (selectedID == data.id) {
                txtEditView.show()
                displayView.hide()

                edtFirstName.setText(data.firstName)
                edtLastName.setText(data.lastName)
                edtMobile.text = data.mobileNumber

                if (data.email.isEmpty() || data.email ==  data.mobileNumber){
                    edtEmail.hide()
                }else {
                    edtEmail.show()
                    edtEmail.text = data.email

                }

            } else {

                txtEditView.hide()
                displayView.show()

                txtFirstName.text = data.firstName
                txtLastName.text = data.lastName
                txtMobile.text = data.mobileNumber
                txtEmail.text = data.email

                if ( data.mobileNumber == data.email){
                    txtEmail.hide()
                }else {
                    txtEmail.show()
                }

            }


            itemview.setOnClickListener {
                selectedID = list[position].id

                edtFirstName.setText(data.firstName)
                edtLastName.setText(data.lastName)
                notifyDataSetChanged()
            }

            btnUpdateContact.setOnClickListener {

                val fname = edtFirstName.text.toString().trim()
                val lname = edtLastName.text.toString().trim()

                if (fname.isEmpty()) {
                    homeActivity.showToast("Please enter first name")
                } else if (lname.isEmpty()) {
                    homeActivity.showToast("Please enter last name")
                }
                else {

                    data = list[position]
                    selectedID = -1

                    data.firstName = fname
                    data.lastName = lname
                    notifyDataSetChanged()
                    homeActivity.updateList(list,data)
                }
            }
        }
    }

    fun reaload(contactList: List<UserContact>) {
        this.list = contactList
        notifyDataSetChanged()

    }
}