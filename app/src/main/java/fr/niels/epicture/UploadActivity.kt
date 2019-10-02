package fr.niels.epicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UploadActivity : BaseActivity(1) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        setupBottomNavigation()
    }
}
