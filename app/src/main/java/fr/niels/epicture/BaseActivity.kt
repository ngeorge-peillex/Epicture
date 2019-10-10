package fr.niels.epicture

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.core.FuelManager
import fr.niels.epicture.model.AuthPayload
import fr.niels.epicture.ui.activity.HomeActivity
import fr.niels.epicture.ui.ProfileActivity
import fr.niels.epicture.ui.activity.UploadActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*

abstract class BaseActivity(val navNumber: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FuelManager.instance.addRequestInterceptor(AuthPayload.interceptor())

    }

    fun setupBottomNavigation() {
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val nextActivity =
                when(it.itemId) {

                    R.id.nav_item_home -> HomeActivity::class.java
                    R.id.nav_item_upload -> UploadActivity::class.java
                    R.id.nav_item_profile -> ProfileActivity::class.java
                    else -> {
                        null
                    }
                }
            if (nextActivity != null) {
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                overridePendingTransition(0, 0)
                startActivity(intent)

                true
            } else {
                false
            }

        }
        bottom_navigation_view.menu.getItem(navNumber).isChecked = true
    }

}