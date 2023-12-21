package com.example.hotel_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table $TABLE_USER ($USER_USER_ID integer primary key, $USER_FIRST_NAME text, " +
                    "$USER_SURNAME text, $USER_PHONE_NUMBER text, $USER_PASSWORD text)"
        )
        db.execSQL(
            "create table $TABLE_RESERVATION ($RESERVATION_RESERVATION_ID integer primary key, " +
                    "$RESERVATION_USER_ID integer, $RESERVATION_ROOM_TYPE text, $RESERVATION_NUMBER_OF_BEDS integer, " +
                    "$RESERVATION_START_DATE text, $RESERVATION_END_DATE text)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $TABLE_USER")
        db.execSQL("drop table if exists $TABLE_RESERVATION")
        onCreate(db)
    }

    fun insertUser(objUser: User): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(USER_FIRST_NAME, objUser.getFirstName())
        cv.put(USER_SURNAME, objUser.getSurname())
        cv.put(USER_PHONE_NUMBER, objUser.getPhoneNumber())
        cv.put(USER_PASSWORD, objUser.getPassword())
        val result = db.insert(TABLE_USER, null, cv)

        if(result != -1L) {
            DBHelper.CURRENT_USER_ID = this.getIdByPhoneNumber(objUser.getPhoneNumber())
            return true
        } else {
            return false
        }
    }

    fun insertReservation(objRes: Reservation): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(RESERVATION_USER_ID, objRes.getUserId())
        cv.put(RESERVATION_ROOM_TYPE, objRes.getRoomType())
        cv.put(RESERVATION_NUMBER_OF_BEDS, objRes.getNumberOfBeds())
        cv.put(RESERVATION_START_DATE, objRes.getStartDate())
        cv.put(RESERVATION_END_DATE, objRes.getEndDate())
        val result = db.insert(TABLE_RESERVATION, null, cv)

        return result != -1L
    }

    fun loginUser(phoneNumber: String?, password: String?): Boolean {

        val db: SQLiteDatabase = this.readableDatabase
        val fields = arrayOf("phone_number", "password", "user_id")
        val values = arrayOf(phoneNumber, password)

        val result = db.query("user",
            fields,
            "phone_number = ? and password = ?",
            values,
            null,
            null,
            null)

        if(result.count > 0) {
            result.moveToFirst()
            if(result.getColumnIndex(USER_USER_ID) >= 0) {
                CURRENT_USER_ID = result.getInt(result.getColumnIndex(USER_USER_ID))
            }
            return true
        } else {
            return false
        }
    }

    fun getIdByPhoneNumber(phoneNumber: String?): Int {
        var userId = 0

        val db: SQLiteDatabase = this.readableDatabase
        val fields = arrayOf("phone_number", "user_id")
        val values = arrayOf(phoneNumber)

        val result = db.query("user",
            fields,
            "phone_number = ?",
            values,
            null,
            null,
            null)

        result.moveToFirst()
        if(result.getColumnIndex(USER_USER_ID) >= 0) {
            userId = result.getInt(result.getColumnIndex(USER_USER_ID))
        }

        return userId
    }

    fun getUserByUserId(userId : Int?) : String? {
        var userInfo : String? = ""

        val db: SQLiteDatabase = this.readableDatabase
        val fields = arrayOf(USER_FIRST_NAME, USER_SURNAME, USER_PHONE_NUMBER, USER_PASSWORD)
        val values = arrayOf(userId.toString())

        val result = db.query("user",
            fields,
            "user_id = ?",
            values,
            null,
            null,
            null)

        result.moveToFirst()
        if(result.getColumnIndex(USER_FIRST_NAME) >= 0) {
            userInfo += result.getString(result.getColumnIndex(USER_FIRST_NAME)) + " "
        }
        if(result.getColumnIndex(USER_SURNAME) >= 0) {
            userInfo += result.getString(result.getColumnIndex(USER_SURNAME)) + " "
        }
        if(result.getColumnIndex(USER_PHONE_NUMBER) >= 0) {
            userInfo += "НТ: " + result.getString(result.getColumnIndex(USER_PHONE_NUMBER)) + " "
        }
        if(result.getColumnIndex(USER_PASSWORD) >= 0) {
            userInfo += "П: " + result.getString(result.getColumnIndex(USER_PASSWORD))
        }
        result.close()

        return userInfo
    }

    fun getReservationsByUserId(userId : Int?) : String? {
        var reservationInfo : String? = ""

        val db: SQLiteDatabase = this.readableDatabase
        val fields = arrayOf(RESERVATION_RESERVATION_ID, RESERVATION_ROOM_TYPE,
            RESERVATION_NUMBER_OF_BEDS, RESERVATION_START_DATE, RESERVATION_END_DATE)
        val values = arrayOf(userId.toString())

        val result = db.query("reservation",
            fields,
            "user_id = ?",
            values,
            null,
            null,
            null)

        result.moveToFirst()
        if(result.count == 0) return "Немає"
        for(i in 1..result.count) {
            if(result.getColumnIndex(RESERVATION_RESERVATION_ID) >= 0) {
                reservationInfo += result.getString(result.getColumnIndex(RESERVATION_RESERVATION_ID)) + " "
            }
            if(result.getColumnIndex(RESERVATION_ROOM_TYPE) >= 0) {
                reservationInfo += "Тип: " + result.getString(result.getColumnIndex(RESERVATION_ROOM_TYPE)) + " "
            }
            if(result.getColumnIndex(RESERVATION_NUMBER_OF_BEDS) >= 0) {
                reservationInfo += "Місць: " + result.getString(result.getColumnIndex(RESERVATION_NUMBER_OF_BEDS)) + " "
            }
            if(result.getColumnIndex(RESERVATION_START_DATE) >= 0) {
                reservationInfo += "Від: " + result.getString(result.getColumnIndex(RESERVATION_START_DATE)) + " "
            }
            if(result.getColumnIndex(RESERVATION_END_DATE) >= 0) {
                reservationInfo += "До: " + result.getString(result.getColumnIndex(RESERVATION_END_DATE))
            }
            reservationInfo += "\n"
            result.moveToNext()
        }
        result.close()

        return reservationInfo
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "hotel_db"

        const val TABLE_USER = "user"
        const val USER_USER_ID = "user_id"
        const val USER_FIRST_NAME = "first_name"
        const val USER_SURNAME = "surname"
        const val USER_PHONE_NUMBER = "phone_number"
        const val USER_PASSWORD = "password"

        const val TABLE_RESERVATION = "reservation"
        const val RESERVATION_RESERVATION_ID = "reservation_id"
        const val RESERVATION_USER_ID = "user_id"
        const val RESERVATION_ROOM_TYPE = "room_type"
        const val RESERVATION_NUMBER_OF_BEDS = "number_of_beds"
        const val RESERVATION_START_DATE = "start_date"
        const val RESERVATION_END_DATE = "end_date"

        var CURRENT_USER_ID = 0
    }
}