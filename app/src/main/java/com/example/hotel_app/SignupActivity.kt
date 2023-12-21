package com.example.hotel_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class SignupActivity : ComponentActivity() {

    private var dbHelper: DBHelper? = null

    var firstName: EditText? = null
    var surname: EditText? = null
    var password: EditText? = null
    var phoneNumber: EditText? = null

    var firstNameError: TextView? = null
    var surnameError: TextView? = null
    var passwordError: TextView? = null
    var phoneNumberError: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dbHelper = DBHelper(applicationContext)

        firstName = findViewById<View>(R.id.signup_firstname_field) as EditText
        surname = findViewById<View>(R.id.signup_surname_field) as EditText
        phoneNumber = findViewById<View>(R.id.signup_phone_number_field) as EditText
        password = findViewById<View>(R.id.signup_password_field) as EditText

        firstNameError = findViewById<View>(R.id.signup_first_name_error) as TextView
        surnameError = findViewById<View>(R.id.signup_surname_error) as TextView
        phoneNumberError = findViewById<View>(R.id.signup_phone_number_error) as TextView
        passwordError = findViewById<View>(R.id.signup_password_error) as TextView
    }

    fun onClickSignup(view: View) {
        var firstNameText = firstName?.text.toString()
        var surnameText = surname?.text.toString()
        var phoneNumberText = phoneNumber?.text.toString()
        var passwordText = password?.text.toString()

        if(signupValidation(firstNameText, surnameText, phoneNumberText, passwordText)) {
            dbHelper?.insertUser(User(firstNameText, surnameText, phoneNumberText, passwordText))

            val signupIntent = Intent(this, MainActivity::class.java)
            startActivity(signupIntent)
        }
    }

    fun signupValidation(firstName: String, surname: String, phoneNumber: String, password: String) : Boolean {
        var result : Boolean = true

        if(firstName == "") {
            firstNameError?.setText(R.string.empty_field_error)
            result = false
        } else {
            firstNameError?.setText("")
        }
        if(surname == "") {
            surnameError?.setText(R.string.empty_field_error)
            result = false
        } else {
            surnameError?.setText("")
        }
        if(phoneNumber == "") {
            phoneNumberError?.setText(R.string.empty_field_error)
            result = false
        } else {
            phoneNumberError?.setText("")
        }
        if(password == "") {
            passwordError?.setText(R.string.empty_field_error)
            result = false
        } else {
            passwordError?.setText("")
        }

        return result
    }

    fun onClickGoBack(view: View) {
        val startIntent = Intent(this, StartActivity::class.java)
        startActivity(startIntent)
    }
}