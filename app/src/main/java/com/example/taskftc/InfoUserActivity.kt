package com.example.taskftc

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskftc.data.models.UserRead
import com.example.taskftc.databinding.ActivityInfoUserBinding
import com.example.taskftc.domain.TransformImage
import com.squareup.picasso.Picasso


class InfoUserActivity : AppCompatActivity() {
    private lateinit var infoClass: ActivityInfoUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infoClass = ActivityInfoUserBinding.inflate(layoutInflater)
        setContentView(infoClass.root)
        supportActionBar?.hide()

        @Suppress("DEPRECATION") val user = intent.getSerializableExtra("user") as UserRead

        Picasso.get().load(user.photo).transform(TransformImage()).into(infoClass.Photo)

        infoClass.DOB.text = "${user.birthday} (${user.age})"
        infoClass.Name.text = user.name
        infoClass.Phone.text = user.phone
        val email: List<String> = user.email.split("@")
        infoClass.Email.text = "${email[0]}@\n${email[1]}"
        infoClass.Address.text = user.location
        infoClass.Nationality.text = user.nationality
        infoClass.Timezone.text = "${user.nameTimezone} (${user.timezone})"
        infoClass.Gender.text = user.gender

        infoClass.Phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${user.phone}")
            }
            startActivity(intent)
        }

        infoClass.Address.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=${user.location}")
                setPackage("com.google.android.apps.maps")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        infoClass.Email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${user.email}")
                putExtra(Intent.EXTRA_SUBJECT, "")
            }

            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            }
        }
    }
}
