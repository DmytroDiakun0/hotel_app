package com.example.hotel_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.util.Calendar


class BookingActivity : ComponentActivity(), View.OnClickListener {
    private var dbHelper: DBHelper? = null

    private var btnStartDatePicker: Button? = null
    private var btnEndDatePicker: Button? = null
    private var editTextStartDate: TextView? = null
    private var editTextEndDate: TextView? = null
    private var roomTypeSpinner: Spinner? = null
    private var bedCountSpinner: Spinner? = null

    var startYear: Int = 0
    var startMonth: Int = 0
    var startDay: Int = 0
    var startDate: String? = ""

    var endYear: Int = 0
    var endMonth: Int = 0
    var endDay: Int = 0
    var endDate: String? = ""

    var selectedRoomType: String? = "Економ"
    var selectedBedCount: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        dbHelper = DBHelper(applicationContext)

        btnStartDatePicker = findViewById<View>(R.id.booking_start_date_button) as Button
        btnEndDatePicker = findViewById<View>(R.id.booking_end_date_button) as Button

        editTextStartDate = findViewById<View>(R.id.booking_start_date_label) as TextView
        editTextEndDate = findViewById<View>(R.id.booking_end_date_label) as TextView

        roomTypeSpinner = findViewById<View>(R.id.booking_room_type_select) as Spinner
        bedCountSpinner = findViewById<View>(R.id.booking_beds_count_select) as Spinner

        btnStartDatePicker!!.setOnClickListener(this)
        btnEndDatePicker!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.booking_start_date_button ->
                callstartDatePicker()

            R.id.booking_end_date_button ->
                callEndDatePicker()
        }
    }

    private fun callEndDatePicker() {
        val cal = Calendar.getInstance()
        endYear = cal[Calendar.YEAR]
        endMonth = cal[Calendar.MONTH]
        endDay = cal[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(this,
            { view, year, monthOfYear, dayOfMonth ->
                endDate = dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year
                editTextEndDate!!.setText("До " + endDate)
            }, endYear, endMonth, endDay
        )
        datePickerDialog.show()
        editTextEndDate?.setTextColor(R.color.black)
    }

    private fun callstartDatePicker() {
        val cal = Calendar.getInstance()
        startYear = cal[Calendar.YEAR]
        startMonth = cal[Calendar.MONTH]
        startDay = cal[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(this,
            { view, year, monthOfYear, dayOfMonth ->
                startDate = dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year
                editTextStartDate!!.setText("Від " + startDate)
            }, startYear, startMonth, startDay
        )
        datePickerDialog.show()
        editTextStartDate?.setTextColor(R.color.black)
    }

    fun onClickBooking(view: View) {
        selectedRoomType = roomTypeSpinner?.selectedItem.toString()

        var stringBedCount: String? = bedCountSpinner?.selectedItem.toString()
        if(stringBedCount == "Одномісний") {
            selectedBedCount = 1
        } else if(stringBedCount == "Двомісний") {
            selectedBedCount = 2
        } else {
            selectedBedCount = 3
        }

        if(bookingValidation()) {
            dbHelper?.insertReservation(
                Reservation(
                    DBHelper.CURRENT_USER_ID,
                    selectedRoomType,
                    selectedBedCount,
                    startDate,
                    endDate
                )
            )

            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    fun bookingValidation() : Boolean {
        var result : Boolean = true

        if(startDate == "") {
            editTextStartDate?.setText("Виберіть дату: ")
            editTextStartDate?.setTextColor(R.color.error)
            result = false
        }
        if(endDate == "") {
            editTextEndDate?.setText("Виберіть дату: ")
            editTextEndDate?.setTextColor(R.color.error)
            result = false
        }

        return result
    }

    fun onClickGoBack(view: View) {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
}