package fr.niels.epicture.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.niels.epicture.BR
import fr.niels.epicture.BaseActivity
import fr.niels.epicture.R
import fr.niels.epicture.provider.GalleryProvider
import fr.niels.epicture.provider.UserProvider
import fr.niels.epicture.ui.adapter.GalleryAdapter
import fr.niels.epicture.viewModel.UserViewModel

class ProfileActivity : BaseActivity(2) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userProvider = UserProvider()
        userProvider.get()

        val userViewModel = UserViewModel(userProvider.user)
        val binding =
            DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_profile)
        binding.setVariable(BR.user, userViewModel)

        setupBottomNavigation()

        val galleryProvider = GalleryProvider()
        galleryProvider.get()

        viewManager = LinearLayoutManager(this)
        viewAdapter = GalleryAdapter(galleryProvider.gallery)

        recyclerView = findViewById<RecyclerView>(R.id.galleryProfile).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }

    }
}
