package com.example.tugasbesarpsi.`class`

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.tugasbesarpsi.R

class LoadingDialog(mActivity: Activity) {
    private var activity: Activity
    private lateinit var dialog: AlertDialog

    init {
        activity = mActivity
    }

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)

        val inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}