package fr.niels.epicture.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import fr.niels.epicture.BR
import fr.niels.epicture.model.Gallery
import fr.niels.epicture.model.Image
import java.util.*

class GalleryViewModel(private val gallery: Gallery) : Observer, BaseObservable() {
    /// Register itself as the observer of Model
    init {
        gallery.addObserver(this)
    }

    val images: MutableList<Image>
        @Bindable get() {
            return gallery.images
        }

    /// Notify the UI when change event emitting from Model is received.
    override fun update(p0: Observable?, p1: Any?) {
        if (p1 is String) {
            if (p1 == "images") {
                notifyPropertyChanged(BR.images)
            }
        }
    }
}
