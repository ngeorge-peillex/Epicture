package fr.niels.epicture.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.niels.epicture.BaseActivity
import fr.niels.epicture.R
import fr.niels.epicture.provider.GalleryProvider
import fr.niels.epicture.ui.adapter.GalleryAdapter

class HomeActivity : BaseActivity(0) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setupBottomNavigation()

        val galleryProvider = GalleryProvider()
        galleryProvider.get()

        viewManager = LinearLayoutManager(this)
        viewAdapter = GalleryAdapter(galleryProvider.gallery)

        recyclerView = findViewById<RecyclerView>(R.id.gallery).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

}
