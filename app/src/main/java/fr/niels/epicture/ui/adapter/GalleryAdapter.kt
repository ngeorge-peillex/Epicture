package fr.niels.epicture.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import fr.niels.epicture.BR
import fr.niels.epicture.R
import fr.niels.epicture.model.Gallery
import fr.niels.epicture.model.Image
import fr.niels.epicture.viewModel.ImageViewModel
import java.util.*

class GalleryAdapter(private val gallery: Gallery) : Observer,
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    init {
        gallery.addObserver(this)
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.setVariable(BR.image, ImageViewModel(image))
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val galleryView = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.gallery_view,
            parent,
            false
        ) as ViewDataBinding
        return ViewHolder(galleryView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gallery.images[position])
    }

    override fun getItemCount() = gallery.images.size

    override fun update(p0: Observable?, p1: Any?) {
        notifyDataSetChanged()
    }
}