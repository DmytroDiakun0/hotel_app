package com.example.hotel_app

class User(firstName: String?, surname: String?, phoneNumber: String?, password: String?) {
    private var firstName: String? = firstName
    private var surname: String? = surname
    private var phoneNumber: String? = phoneNumber
    private var password: String? = password

    fun getFirstName(): String? {
        return firstName
    }

    fun getSurname(): String? {
        return surname
    }

    fun getPhoneNumber(): String? {
        return phoneNumber
    }

    fun getPassword(): String? {
        return password
    }

    fun setFirstName(firstName: String?) {
        this.firstName = firstName
    }

    fun setSurname(surname: String?) {
        this.surname = surname
    }

    fun setPhoneNumber(phoneNumber: String?) {
        this.phoneNumber = phoneNumber
    }

    fun setPassword(password: String?) {
        this.password = password
    }
}