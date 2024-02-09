package com.example.taskftc.data.models

import java.io.Serializable

data class UserRead (val id: Int, val name: String, val gender: String, val age: String, val birthday: String, val location: String,
                    val timezone: String, val nameTimezone: String, val email: String, val phone: String,
                    val photo: String, val nationality: String) : Serializable
