package fr.niels.epicture.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.niels.epicture.BaseActivity
import fr.niels.epicture.R
import fr.niels.epicture.provider.GalleryProvider
import fr.niels.epicture.ui.adapter.GalleryAdapter


class HomeActivity : BaseActivity(0) {
    private val tag = "HomeActivity"

    private lateinit var recyclerView: RecyclerView

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

    var searchQuery: String = ""

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
                if (searchQuery.isNotEmpty())
                    galleryProvider.searchGallery(filters[filterIndex], searchQuery)
                else
                    galleryProvider.getTrends(filters[filterIndex])
            }
        })

        rightArrowFilterButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                filterIndex += 1
                filterTextField.text = filters[filterIndex]
                if (searchQuery.isNotEmpty())
                    galleryProvider.searchGallery(filters[filterIndex], searchQuery)
                else
                    galleryProvider.getTrends(filters[filterIndex])
            }
        })
    }

    private fun configureSearchView() {
        val searchView = findViewById<SearchView>(R.id.searchBar)
        searchView.queryHint = "Search Images"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    searchQuery = query
                    galleryProvider.searchGallery(filters[filterIndex], searchQuery)
                } else {
                    galleryProvider.getTrends(filters[filterIndex])
                    searchQuery = ""
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    galleryProvider.getTrends(filters[filterIndex])
                    searchQuery = ""
                }
                return false
            }
        })
    }

    private fun buildRecyclerView() {
        galleryProvider.getTrends(filters[filterIndex])

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = GalleryAdapter(galleryProvider.gallery)

        recyclerView = findViewById<RecyclerView>(R.id.gallery).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = viewManager.childCount
                val totalItemCount = viewManager.itemCount
                val firstVisibleItemPosition = viewManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - 10
                    && firstVisibleItemPosition >= 0
                    && !galleryProvider.isInAsyncTask
                ) {
                    if (searchQuery.isNotEmpty()) {
                        galleryProvider.searchGallery(
                            filters[filterIndex],
                            searchQuery,
                            nextPage = true
                        )
                    } else {
                        galleryProvider.getTrends(filters[filterIndex], nextPage = true)
                    }
                }
            }
        })
    }

}
