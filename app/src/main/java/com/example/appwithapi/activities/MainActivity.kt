package com.example.appwithapi.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appwithapi.models.DefaultResponse
import com.example.appwithapi.R
import com.example.appwithapi.api.RetrofitClient
import com.example.appwithapi.storage.SharedPreferenceManager
import com.example.appwithapi.utils.Utils
import kotlinx.android.synthetic.main.sign_up_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)


        singUpBtn.setOnClickListener {
            UserSignUp()
        }

        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    @Override
    override fun onStart () {
        super.onStart()
        if(SharedPreferenceManager(this@MainActivity).getInstance(this@MainActivity).isLoggedIn()) {
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }

    }

    private fun UserSignUp() {
        val email = emailTextView.text.toString()
        val password = passwordTextView.text.toString()
        val name = nameTextView.text.toString()
        val school = schoolTextView.text.toString()
        val address = addressTextView.text.toString()


        if (checkFieldsIfEmpty()) {
            Utils.showAltrDialogWithNotNegative(
                this, "Input All Info"
            ) {}
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utils.showAltrDialogWithNotNegative(
                this, "Enter a valid email"
            ) {}

        } else {

            val call: Call<DefaultResponse> = RetrofitClient()
                .getInstance()
                .getApi()
                .createuser(email, password, name, school, address)


            call.enqueue(object : Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {

                    var defaultResponse: DefaultResponse?
                    if (response.code() == 201) {
                        defaultResponse = response.body()
                        Toast.makeText(this@MainActivity, defaultResponse!!.getMsg(), Toast.LENGTH_LONG)
                            .show()

                    } else if (response.code() == 422){
                        Toast.makeText(this@MainActivity, "User Already Exist", Toast.LENGTH_LONG)
                            .show()
                    }

                }

            })

        }

    }

    private fun checkFieldsIfEmpty(): Boolean {
        val list = mutableListOf(
            emailTextView, passwordTextView, nameTextView, schoolTextView, addressTextView
        )
        for (editBox in list) {
            if (editBox.text.toString().trim().isEmpty()) {
                return true
            }
        }
        return false
    }

}
