package com.application.utils.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class ActBase<T : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var mBinding: T
    private lateinit var progressDialog: Dialog

    protected fun bindView(layoutId: Int) {
        mBinding = DataBindingUtil.setContentView(this, layoutId)

    }

    fun isInternetAvailable(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }



    fun showAlert(msg: String?, isFinish: Boolean) {

        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("Okay") { dialog, which ->
                if (isFinish)
                    finish()
            }

        alertDialog.create().show()
    }

    fun showToast(msg: String?) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show()
    }

    fun showNoData(
        recyclerView: RecyclerView,
        noDataView: View,
        isNullOrEmpty: Boolean,
        msg: String?
    ) {

        if (isNullOrEmpty) {

            recyclerView.visibility = View.GONE
            noDataView.visibility = View.VISIBLE

        } else {

            recyclerView.visibility = View.VISIBLE
            noDataView.visibility = View.GONE
        }
    }



    protected fun dismissLoader() {
        if (::progressDialog.isInitialized)
            progressDialog.dismiss()
    }

    fun startMyActivity(context: Context,intent: Intent){
        startActivity(intent)
    }

    fun startMyActivityReferesh(context: Context,intent: Intent){
        startActivity(intent)
        finish()
    }

    fun displayShortToast(context: Context, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun displayLongToast(context: Context, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

    open fun parseDateToddMMyyyy(time: String?): String? {
        val inputPattern = "dd/MM/yyyy"
        val outputPattern = "yyyy-MM-dd"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

}