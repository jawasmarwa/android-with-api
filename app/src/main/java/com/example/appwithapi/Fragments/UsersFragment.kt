package com.example.appwithapi.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appwithapi.R
import com.example.appwithapi.adapters.UsersAdapter
import com.example.appwithapi.api.RetrofitClient
import com.example.appwithapi.models.DefaultResponse
import com.example.appwithapi.models.User
import com.example.appwithapi.models.UsersResponse
import kotlinx.android.synthetic.main.fragment_users.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class UsersFragment: Fragment() {

    var usersList : List<User>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersRecyclerView.layoutManager = LinearLayoutManager(activity)

        val call: Call<UsersResponse> = RetrofitClient().getInstance().getApi().allusers()
        call.enqueue(object : Callback<UsersResponse >{
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                usersList = response.body()!!.users
                //Log.d("TAG", "users $usersList")
                val adapter = UsersAdapter(activity!!, usersList!!)
                usersRecyclerView.adapter = adapter
            }

        })

    }
}