package com.example.taskftc.data

import android.provider.BaseColumns

object DbObject : BaseColumns {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "HabitsData.db"
    const val TABLE_NAME = "Habits"

    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_GENDER = "gender"
    const val COLUMN_NAME_AGE = "age"
    const val COLUMN_NAME_DOB = "date_of_birthday"
    const val COLUMN_NAME_LOCATION = "location"
    const val COLUMN_NAME_TIMEZONE = "timezone"
    const val COLUMN_NAME_TIMEZONE_DES = "timezone_name"
    const val COLUMN_NAME_EMAIL = "email"
    const val COLUMN_NAME_PHONE = "phone"
    const val COLUMN_NAME_PHOTO = "photo"
    const val COLUMN_NAME_NAT = "nationality"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY,$COLUMN_NAME_NAME TEXT," +
            "$COLUMN_NAME_GENDER TEXT,$COLUMN_NAME_AGE TEXT,$COLUMN_NAME_DOB TEXT," +
            "$COLUMN_NAME_LOCATION TEXT,$COLUMN_NAME_TIMEZONE TEXT,$COLUMN_NAME_TIMEZONE_DES TEXT," +
            "$COLUMN_NAME_EMAIL TEXT,$COLUMN_NAME_PHONE TEXT,$COLUMN_NAME_PHOTO TEXT,$COLUMN_NAME_NAT TEXT)"


    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}