package fr.niels.epicture.ui

import android.os.Bundle
import fr.niels.epicture.R

class ProfileActivity : BaseActivity(2) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupBottomNavigation()
    }
}
