package com.example.taskftc.domain.useCase

import android.content.Context
import com.example.taskftc.data.DbManager

class CleanTableUseCase {
    fun execute(context: Context){
        val db = DbManager(context)
        db.openDB()
        db.cleanTable()
        db.closeDB()
    }
}