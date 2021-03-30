package com.example.appwithapi.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Handler
import android.os.Looper

object Utils {

    fun showAltrDialogWithNotNegative(
        activity: Activity,
        msg: String,
        callback: (String) -> Unit
    ) {
        val builder = AlertDialog.Builder(activity)
        //set message for alert dialog
        builder.setMessage(msg)

        //performing positive action
        builder.setPositiveButton("Ok") { _: DialogInterface, _: Int ->
            callback("clicked")
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun showAltrDialogWithNegative(
        activity: Activity,
        title: String,
        msg: String,
        callback: (String) -> Unit
    ) {
        val builder = AlertDialog.Builder(activity)

        //set title
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(msg)

        //performing positive action
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            callback("clicked")
        }

        //performing negative action
        builder.setNegativeButton("No") { _: DialogInterface, _: Int ->

        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}