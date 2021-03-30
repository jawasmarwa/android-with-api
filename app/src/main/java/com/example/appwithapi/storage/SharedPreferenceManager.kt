package com.example.appwithapi.storage

import android.content.Context
import com.example.appwithapi.models.User

class SharedPreferenceManager (mCtx: Context){

    private val SHARED_PREF_NAME = "my_shared_preff"

    private var mInstance : SharedPreferenceManager? = null
    private var mCtx : Context? = null

    init {
        this.mCtx = mCtx
    }

    fun getInstance(mCtx: Context): SharedPreferenceManager {
        if (mInstance == null) {
            synchronized(this) {
                mInstance = SharedPreferenceManager(mCtx)
            }
        }
        return mInstance!!
    }

    public fun saveUser(user: User) {
        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", user.get_id()!!)
        editor.putString("email", user.get_email())
        editor.putString("name", user.get_name())
      //  editor.putString("password", user.get_password())
        editor.putString("school", user.get_school())
        editor.putString("adress", user.get_adress())

        editor.apply()

    }

    public fun isLoggedIn(): Boolean {
        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return sharedPreferences.getInt("id", -1) != -1
    }

    public fun getUser(): User {

        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return User(
            sharedPreferences.getInt("id", -1),
            sharedPreferences.getString("email", null)!!,
            sharedPreferences.getString("name", null)!!,
        //    sharedPreferences.getString("password", null)!!,
            sharedPreferences.getString("school", null)!!,
            sharedPreferences.getString("adress", null)!!
        )
    }

    public fun clear() {
        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}