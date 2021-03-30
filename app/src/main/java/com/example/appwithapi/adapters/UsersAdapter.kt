package com.example.appwithapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appwithapi.R
import com.example.appwithapi.models.User

class UsersAdapter (mCtx : Context, userList: List<User>) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var mCtx : Context? = null
    private var userList: List<User>? = null

    init {
        this.mCtx = mCtx
        this.userList = userList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_users, parent, false)
        return UsersViewHolder(view)

    }

    override fun getItemCount(): Int {
        return userList!!.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = userList!![position]
        holder.userName.text = user.get_name()
        holder.userEmail.text = user.get_email()
        holder.userSchool.text = user.get_school()
        holder.userAdress.text = user.get_adress()

    }

    class UsersViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        val userName = itemView.findViewById<TextView>(R.id.displayNameTxt)
        val userEmail = itemView.findViewById<TextView>(R.id.displayEmailTxt)
        val userSchool = itemView.findViewById<TextView>(R.id.displaySchoolTxt)
        val userAdress = itemView.findViewById<TextView>(R.id.displayAdressTxt)

    }
}