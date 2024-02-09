package com.example.taskftc

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskftc.data.DbManager
import com.example.taskftc.data.GetURLData
import com.example.taskftc.databinding.ActivityMainBinding
import com.example.taskftc.domain.UserAdapter
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var mainClass: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainClass.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val db = DbManager(this)
        db.openDB()
        val result = db.readData()
        db.closeDB()
        if(result.size == 0) {
           getNewData(db)
        }else{
            val recyclerview = mainClass.List
            recyclerview.layoutManager = LinearLayoutManager(this)

            val adapter = UserAdapter(result, this)
            recyclerview.adapter = adapter

            Snackbar.make(findViewById(android.R.id.content), "The data has been uploaded successfully", Snackbar.LENGTH_SHORT)
                .show()

        }
        mainClass.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.update -> {
                    db.openDB()
                    db.cleanTable()
                    db.closeDB()

                    getNewData(db)
                    true
                }
                else -> false
            }
        }
    }

    private fun getNewData(db: DbManager){
        if (isOnline(this)) {
            @Suppress("DEPRECATION")
            GetURLData {
                db.openDB()
                it.forEach {
                    db.insertToDB(it)
                }
                val result = db.readData()
                db.closeDB()
                if (result.size != 0) {
                    val recyclerview = mainClass.List
                    recyclerview.layoutManager = LinearLayoutManager(this)

                    val adapter = UserAdapter(result, this)
                    recyclerview.adapter = adapter

                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "The data has been uploaded successfully",
                        Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Error. The data has not been uploaded",
                        Snackbar.LENGTH_SHORT).show()
                }
            }.execute(URL)
        }else{
            Snackbar.make(
                findViewById(android.R.id.content),
                "There is no internet connection",
                Snackbar.LENGTH_SHORT).show()
        }
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @Suppress("DEPRECATION") val netInfo = cm.activeNetworkInfo
        @Suppress("DEPRECATION")
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}