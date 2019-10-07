package fr.niels.epicture.ui

import android.os.Bundle
import android.widget.ImageView
import fr.niels.epicture.BaseActivity
import fr.niels.epicture.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(0) {

    lateinit var newView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setupBottomNavigation()

        for (item in pictureArray) {

            newView = ImageView(this)

            myLayout.addView(newView)

            newView.layoutParams.height = 1080
            newView.layoutParams.width = 1080
            newView.setImageURI(item.link)

        }

    }
}
