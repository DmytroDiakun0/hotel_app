package com.example.hotel_app

import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity

class AccountActivity : ComponentActivity() {

    private var dbHelper: DBHelper? = null

    var userInfo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        dbHelper = DBHelper(applicationContext)

        userInfo = findViewById<View>(R.id.account_user_info) as TextView
        fillUserInfo()
    }

    fun onClickGoBack(view: View) {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    fun fillUserInfo() {
        userInfo?.setText(dbHelper?.getUserByUserId(DBHelper.CURRENT_USER_ID))
    }
}