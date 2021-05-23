package com.example.isuscorp.misc

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.example.isuscorp.R

class DialogLogin {

    fun showDialog(context: Context){
        MaterialDialog(context).show {
            title(R.string.dialog_login)
            message(R.string.error_login)
            cornerRadius(16f)

            positiveButton {
                it.dismiss()
            }
        }
    }
}