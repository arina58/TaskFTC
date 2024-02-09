package com.example.taskftc.domain.useCase

import android.content.Context
import com.example.taskftc.data.DbManager
import com.example.taskftc.data.models.UserRead

class GetDataFromDBUseCase {
    fun execute(context: Context): ArrayList<UserRead> {
        val db = DbManager(context)
        db.openDB()
        val result = db.readData()
        db.closeDB()

        return result
    }
}