package com.example.hotel_app

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.view.View

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
    }

    fun onClickBooking(view: View) {
        val bookingIntent = Intent(this, BookingActivity::class.java)
        startActivity(bookingIntent)
    }

    fun onClickBookingList(view: View) {
        val bookingListIntent = Intent(this, BookingListActivity::class.java)
        startActivity(bookingListIntent)
    }

    fun onClickAccount(view: View) {
        val accountIntent = Intent(this, AccountActivity::class.java)
        startActivity(accountIntent)
    }

    fun onClickLogout(view: View) {
        DBHelper.CURRENT_USER_ID = 0

        val startIntent = Intent(this, StartActivity::class.java)
        startActivity(startIntent)
    }
}