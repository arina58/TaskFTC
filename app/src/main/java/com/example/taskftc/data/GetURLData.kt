@file:Suppress("DEPRECATION")

package com.example.taskftc.data

import android.os.AsyncTask
import com.example.taskftc.data.models.UserInsert
import com.example.taskftc.data.models.DataClassUser
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection



@Suppress("DEPRECATION")
class GetURLData(private val callback: (List<UserInsert>) -> Unit) : AsyncTask<String, String,  List<DataClassUser.User>>(){
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg p0: String?): List<DataClassUser.User> {

        val url = URL(p0[0])
        val connection = url.openConnection() as HttpsURLConnection
        connection.connect()

        val stream: InputStream = connection.inputStream

        val line = BufferedReader(InputStreamReader(stream)).readLine()

        val results = Gson().fromJson(line, DataClassUser.Users::class.java)

        return results.results
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: List<DataClassUser.User>?) {
        super.onPostExecute(result)
        result?.let { users ->
            var userInsertList = ArrayList<UserInsert>()
            users.forEach { it ->
                val userInsert = UserInsert(
                gender = it.gender,
                name = "${it.name.title} ${it.name.first} ${it.name.last}",
                age = it.dob.age,
                birthday = it.dob.date.substring(0, 10),
                location = "${it.location.country}, ${it.location.city}, ${it.location.street.name} ${it.location.street.number}",
                timezone = it.location.timezone.offset,
                nameTimezone = it.location.timezone.description,
                email = it.email,
                phone = it.phone,
                nationality = it.nat,
                photo = it.picture.large
                )
                userInsertList.add(userInsert)
            }

            callback.invoke(userInsertList)
        }
    }
}
