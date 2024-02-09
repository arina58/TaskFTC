package com.example.taskftc.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.taskftc.data.models.UserInsert
import com.example.taskftc.data.models.UserRead

class DbManager(context: Context) {
    private val myDBHelper = DBHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDB(){
        db = myDBHelper.writableDatabase
    }

    fun insertToDB(userInsert: UserInsert){
        val values = ContentValues().apply {
            put(DbObject.COLUMN_NAME_NAME, userInsert.name)
            put(DbObject.COLUMN_NAME_GENDER, userInsert.gender)
            put(DbObject.COLUMN_NAME_AGE, userInsert.age)
            put(DbObject.COLUMN_NAME_DOB, userInsert.birthday)
            put(DbObject.COLUMN_NAME_LOCATION, userInsert.location)
            put(DbObject.COLUMN_NAME_TIMEZONE, userInsert.timezone)
            put(DbObject.COLUMN_NAME_TIMEZONE_DES, userInsert.nameTimezone)
            put(DbObject.COLUMN_NAME_EMAIL, userInsert.email)
            put(DbObject.COLUMN_NAME_PHONE, userInsert.phone)
            put(DbObject.COLUMN_NAME_PHOTO, userInsert.photo)
            put(DbObject.COLUMN_NAME_NAT, userInsert.nationality)
        }

        db?.insert(DbObject.TABLE_NAME, null, values)

    }

    @SuppressLint("Range")
    fun readData(): ArrayList<UserRead>{
        var result = ArrayList<UserRead>()

        val cursor = db?.query(DbObject.TABLE_NAME, null, null, null, null, null, null)


        while (cursor?.moveToNext()!!){
            val id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_NAME))
            val gender = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_GENDER))
            val age = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_AGE))
            val birthday = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_DOB))
            val location = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_LOCATION))
            val timezone = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_TIMEZONE))
            val nameTimezone = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_TIMEZONE_DES))
            val email = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_EMAIL))
            val phone = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_PHONE))
            val photo = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_PHOTO))
            val nationality = cursor.getString(cursor.getColumnIndex(DbObject.COLUMN_NAME_NAT))

            val user = UserRead(id.toInt(), name, gender, age, birthday, location, timezone, nameTimezone, email, phone, photo, nationality)
            result.add(user)
        }
        cursor.close()
        return result
    }

    fun closeDB(){
        myDBHelper.close()
    }

    fun cleanTable(){
        db?.delete(DbObject.TABLE_NAME, null, null)
    }
}