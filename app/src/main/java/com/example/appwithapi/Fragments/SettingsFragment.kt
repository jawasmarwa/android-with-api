package com.example.appwithapi.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appwithapi.R
import com.example.appwithapi.activities.LoginActivity
import com.example.appwithapi.activities.MainActivity
import com.example.appwithapi.api.RetrofitClient
import com.example.appwithapi.models.DefaultResponse
import com.example.appwithapi.models.LoginResponse
import com.example.appwithapi.storage.SharedPreferenceManager
import com.example.appwithapi.utils.Utils
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.sign_up_layout.*
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveProfileUpateBtn.setOnClickListener {
            saveProfileChanges()
        }

        savePasswordUpateBtn.setOnClickListener {
            savePasswordChanges()
        }

        logoutBtn.setOnClickListener {
            logout()
        }

        deleteUserBtn.setOnClickListener {
            deleteUser()
        }

    }

    private fun saveProfileChanges() {

        val email = emailEditTxt.text.toString().trim()
        val name = nameEditTxt.text.toString().trim()
        val school = schoolEditTxt.text.toString().trim()
        val address = adressEditTxt.text.toString().trim()

        if (checkFieldsIfEmptyOfProfile()) {
            Utils.showAltrDialogWithNotNegative(
                activity!!, "Input All Info"
            ) {}
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utils.showAltrDialogWithNotNegative(
                activity!!, "Enter a valid email"
            ) {}
        } else {

            val user = SharedPreferenceManager(activity!!).getInstance(activity!!).getUser()

            val call = RetrofitClient().getInstance().getApi().updateuser(
                user.get_id()!!, email, name, school, address
            )

            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    Toast.makeText(activity, response.body()!!.get_message(), Toast.LENGTH_LONG)
                        .show()

                    if (!response.body()!!.get_error()!!) {
                        SharedPreferenceManager(activity!!).getInstance(activity!!)
                            .saveUser(response.body()!!.get_user()!!)
                    }
                }

            })
        }
    }

    private fun savePasswordChanges() {
        val currentPassword = currentPasswordTxt.text.toString().trim()
        val newPassword = newPasswordTxt.text.toString().trim()

        Log.d("###", "Emailllllllll${newPassword}")

        if (checkFieldsIfEmptyOfPassword()) {
            Utils.showAltrDialogWithNotNegative(
                activity!!, "Fill Password Fields"
            ) {}
        } else {

            val user = SharedPreferenceManager(activity!!).getInstance(activity!!).getUser()

            Log.d("###", "Emailllllllll${user.email}")

            val call = RetrofitClient().getInstance().getApi().updateuserpassword(
                currentPassword, newPassword, user.get_email()!!
            )

            call.enqueue(object : Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {

                    Log.d("###", "Emailllllllll${user.name}")


                    Toast.makeText(activity, response.body()!!.getMsg(), Toast.LENGTH_LONG)
                        .show()

                }

            })
        }
    }

    private fun logout(){
        SharedPreferenceManager(activity!!).getInstance(activity!!).clear()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun deleteUser() {
        Utils.showAltrDialogWithNegative(
            activity!!, "Warning", "Are you sure you want to delete user...."
        ) {

            val user = SharedPreferenceManager(activity!!).getInstance(activity!!).getUser()

            //Log.d("###", "User${user.get_name()}")
            val call = RetrofitClient().getInstance().getApi().deleteuser(
                user.get_id()!!
            )

            call.enqueue(object : Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {

                    Log.d("###", "User${user.get_name()}")
                    if(!response.body()!!.isErr()!!) {

                        SharedPreferenceManager(activity!!).getInstance(activity!!).clear()
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)

                        Toast.makeText(
                            activity!!,
                            response.body()!!.getMsg(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })
        }
    }

    private fun checkFieldsIfEmptyOfProfile(): Boolean {
        val list = mutableListOf(
            emailEditTxt, nameEditTxt, schoolEditTxt, adressEditTxt
        )
        for (editBox in list) {
            if (editBox.text.toString().trim().isEmpty()) {
                return true
            }
        }
        return false
    }


    private fun checkFieldsIfEmptyOfPassword(): Boolean {
        val list = mutableListOf(
            currentPasswordTxt, newPasswordTxt
        )
        for (editBox in list) {
            if (editBox.text.toString().trim().isEmpty()) {
                editBox.requestFocus()
                return true
            }
        }
        return false
    }

}