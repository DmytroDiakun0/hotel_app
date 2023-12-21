package com.example.hotel_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {

    private var dbHelper: DBHelper? = null

    var phoneNumber: EditText? = null
    var password: EditText? = null
    var errorMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DBHelper(applicationContext)

        phoneNumber = findViewById<View>(R.id.login_phone_number_field) as EditText
        password = findViewById<View>(R.id.login_password_field) as EditText
        errorMessage = findViewById<View>(R.id.login_error) as TextView
    }

    fun onClickLogin(view: View) {
        var phoneNumberText = phoneNumber?.text.toString()
        var passwordText = password?.text.toString()

        if(dbHelper?.loginUser(phoneNumberText, passwordText) == false) {
            errorMessage?.setText(R.string.login_error_message)
            return
        }

        val loginIntent = Intent(this, MainActivity::class.java)
        startActivity(loginIntent)
    }

    fun onClickGoBack(view: View) {
        val startIntent = Intent(this, StartActivity::class.java)
        startActivity(startIntent)
    }
}