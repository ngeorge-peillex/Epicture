package fr.niels.epicture.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.niels.epicture.BaseActivity
import fr.niels.epicture.R
import fr.niels.epicture.model.AuthPayload
import fr.niels.epicture.provider.GalleryProvider
import fr.niels.epicture.ui.adapter.GalleryAdapter

class HomeActivity : BaseActivity(0) {
    private val tag = "HomeActivity"

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val galleryProvider = GalleryProvider()

    val filters: List<String> = listOf("Day", "Week", "Month", "Year", "All")
    private var filterIndex: Int = 0
        set(value) {
            if (value < 0) {
                field = filters.size - 1
            } else if (value >= filters.size) {
                field = 0
            } else {
                field = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setupBottomNavigation()

        configureSearchView()

        configureFilters()

        buildRecyclerView()
    }

    private fun configureFilters() {
        val filterButton = findViewById<ImageButton>(R.id.filterButton)
        val filterBar = findViewById<ConstraintLayout>(R.id.filters)

        filterButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                filterBar.visibility =
                    if (filterBar.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
        })

        val leftArrowFilterButton = findViewById<ImageButton>(R.id.arrowLeft)
        val rightArrowFilterButton = findViewById<ImageButton>(R.id.arrowRight)
        val filterTextField = findViewById<TextView>(R.id.filter)

        leftArrowFilterButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                filterIndex -= 1
                filterTextField.text = filters[filterIndex]
                galleryProvider.getTrends(filters[filterIndex])
            }
        })

        rightArrowFilterButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                filterIndex += 1
                filterTextField.text = filters[filterIndex]
                galleryProvider.getTrends(filters[filterIndex])
            }
        })
    }

    private fun configureSearchView() {
        val searchView = findViewById<SearchView>(R.id.searchBar)
        searchView.queryHint = "Search Images"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                galleryProvider.getTrends(filters[filterIndex])
                return false
            }
        })
    }

    private fun buildRecyclerView() {
        galleryProvider.getTrends(filters[filterIndex])

        viewManager = LinearLayoutManager(this)
        viewAdapter = GalleryAdapter(galleryProvider.gallery)

        recyclerView = findViewById<RecyclerView>(R.id.gallery).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

}
