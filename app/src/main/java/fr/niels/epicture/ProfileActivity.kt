package fr.niels.epicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProfileActivity : BaseActivity(2) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupBottomNavigation()
    }
}
