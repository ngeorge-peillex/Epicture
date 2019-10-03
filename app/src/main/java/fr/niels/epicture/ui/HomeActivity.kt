package fr.niels.epicture.ui

import android.os.Bundle
import fr.niels.epicture.R

class HomeActivity : BaseActivity(0) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation()

    }
}
