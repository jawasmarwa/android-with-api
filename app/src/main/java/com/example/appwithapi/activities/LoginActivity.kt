package com.example.appwithapi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appwithapi.R
import com.example.appwithapi.api.RetrofitClient
import com.example.appwithapi.models.DefaultResponse
import com.example.appwithapi.models.LoginResponse
import com.example.appwithapi.storage.SharedPreferenceManager
import com.example.appwithapi.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            login()
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    @Override
    override fun onStart () {
        super.onStart()
        if(SharedPreferenceManager(this@LoginActivity).getInstance(this@LoginActivity).isLoggedIn()) {
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    private fun login() {
        var loginEmail = loginEmailTextView.text.toString().trim()
        var loginPassword = loginPasswordTextView.text.toString().trim()

        if (checkFieldsIfEmpty()) {
            Utils.showAltrDialogWithNotNegative(
                this, "Input All Info"
            ) {}
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
            Utils.showAltrDialogWithNotNegative(
                this, "Enter a valid email"
            ) {}

        } else {

            val call: Call<LoginResponse> = RetrofitClient()
                .getInstance()
                .getApi()
                .userlogin(loginEmail, loginPassword)

            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    val loginResponse = response.body()

//                    Toast.makeText(
//                        this@LoginActivity,
//                        loginResponse?.get_message(),
//                        Toast.LENGTH_LONG
//                    ).show()
//
                    if (response.code() == 200) {
                        SharedPreferenceManager(this@LoginActivity).getInstance(this@LoginActivity)
                            .saveUser(loginResponse!!.get_user()!!)

                        val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
                       // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                        startActivity(intent)


                    } else if (response.code() == 404){
                        Toast.makeText(
                            this@LoginActivity,
                            "Login Faild",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })
        }
    }

    fun checkFieldsIfEmpty(): Boolean {
        val list = mutableListOf(
            loginEmailTextView, loginPasswordTextView
        )
        for (editBox in list) {
            if (editBox.text.toString().trim().isEmpty()) {
                return true
            }
        }
        return false
    }
}

