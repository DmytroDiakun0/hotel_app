package com.example.hotel_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity

class BookingListActivity : ComponentActivity() {
    private var dbHelper: DBHelper? = null

    var reservationInfo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_list)

        dbHelper = DBHelper(applicationContext)

        reservationInfo = findViewById<View>(R.id.reservation_info) as TextView
        fillReservationInfo()
    }

    fun onClickGoBack(view: View) {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    fun fillReservationInfo() {
        reservationInfo?.setText(dbHelper?.getReservationsByUserId(DBHelper.CURRENT_USER_ID))
    }
}