package com.example.hotel_app

class Reservation(userId: Int?, roomType: String?, numberOfBeds: Int?, startDate: String?, endDate: String?) {
    private var userId: Int? = userId
    private var roomType: String? = roomType
    private var numberOfBeds: Int? = numberOfBeds
    private var startDate: String? = startDate
    private var endDate: String? = endDate

    fun getUserId(): Int? {
        return userId
    }

    fun getRoomType(): String? {
        return roomType
    }

    fun getNumberOfBeds(): Int? {
        return numberOfBeds
    }

    fun getStartDate(): String? {
        return startDate
    }

    fun getEndDate(): String? {
        return endDate
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

    fun setRoomType(roomType: String?) {
        this.roomType = roomType
    }

    fun setNumberOfBeds(numberOfBeds: Int?) {
        this.numberOfBeds = numberOfBeds
    }

    fun setStartDate(startDate: String?) {
        this.startDate = startDate
    }

    fun setEndDate(endDate: String?) {
        this.startDate = startDate
    }
}