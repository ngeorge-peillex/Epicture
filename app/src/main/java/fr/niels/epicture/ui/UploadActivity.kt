package fr.niels.epicture.ui

import android.os.Bundle
import fr.niels.epicture.BaseActivity
import fr.niels.epicture.R

class UploadActivity : BaseActivity(1) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_upload)
        setupBottomNavigation()
    }
}
