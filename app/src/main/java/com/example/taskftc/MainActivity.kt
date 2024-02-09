package com.example.taskftc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskftc.data.DbManager
import com.example.taskftc.data.GetURLData
import com.example.taskftc.data.models.UserRead
import com.example.taskftc.databinding.ActivityMainBinding
import com.example.taskftc.domain.UserAdapter
import com.example.taskftc.domain.useCase.CheckInternetUserCase
import com.example.taskftc.domain.useCase.CleanTableUseCase
import com.example.taskftc.domain.useCase.GetDataFromDBUseCase
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var mainClass: ActivityMainBinding
    private val getDataFromDb = GetDataFromDBUseCase()
    private val cleanTable = CleanTableUseCase()
    private val isOnline = CheckInternetUserCase()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainClass.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val result = getDataFromDb.execute(this)
        if(result.size == 0) {
           getNewData()
        }else{
            showUsers(result)
        }
        mainClass.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.update -> {
                    cleanTable.execute(this)
                    getNewData()

                    true
                }
                else -> false
            }
        }
    }

    private fun getNewData() {
        if (isOnline.execute(this)) {
            val db = DbManager(this)
            @Suppress("DEPRECATION")
            GetURLData {
                db.openDB()
                it.forEach {
                    db.insertToDB(it)
                }
                db.closeDB()
                val result = getDataFromDb.execute(this)

                if (result.size != 0) {
                    showUsers(result)
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Error. The data has not been uploaded",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }.execute(URL)
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                "There is no internet connection",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun showUsers(result: ArrayList<UserRead>){
        val recyclerview = mainClass.List
        recyclerview.layoutManager = LinearLayoutManager(this)

        val adapter = UserAdapter(result, this)
        recyclerview.adapter = adapter

        Snackbar.make(findViewById(android.R.id.content), "The data has been uploaded successfully", Snackbar.LENGTH_SHORT)
            .show()
    }
}