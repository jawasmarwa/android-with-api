package com.example.appwithapi.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appwithapi.R
import com.example.appwithapi.storage.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        UserEmail.text = SharedPreferenceManager(activity!!).getInstance(activity!!).getUser().get_email()
        UserName.text = SharedPreferenceManager(activity!!).getInstance(activity!!).getUser().get_name()
        UserSchool.text = SharedPreferenceManager(activity!!).getInstance(activity!!).getUser().get_school()
        UserAdress.text = SharedPreferenceManager(activity!!).getInstance(activity!!).getUser().get_adress()
    }
}