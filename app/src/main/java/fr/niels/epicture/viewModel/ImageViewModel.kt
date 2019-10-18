package fr.niels.epicture.viewModel

import android.graphics.drawable.Drawable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import fr.niels.epicture.BR
import fr.niels.epicture.model.Image
import fr.niels.epicture.provider.ImageProvider
import java.util.*

class ImageViewModel(private val image: Image) : Observer, BaseObservable() {
    /// Register itself as the observer of Model
    init {
        image.addObserver(this)
    }

    val title: String
        @Bindable get() {
            return image.title
        }

    val type: String
        @Bindable get() {
            return image.type
        }

    val content: Drawable
        @Bindable get() {
            return image.content
        }

    val favorite: Boolean
        @Bindable get() {
            return image.favorite
        }

    val owner: String
        @Bindable get() {
            return image.owner
        }

    fun switchFavorite() {
        image.favorite = !image.favorite
        ImageProvider().favorite(image.id)
    }

    /// Notify the UI when change event emitting from Model is received.
    override fun update(p0: Observable?, p1: Any?) {
        if (p1 is String) {
            if (p1 == "title") {
                notifyPropertyChanged(BR.title)
            }
            if (p1 == "type") {
                notifyPropertyChanged(BR.type)
            }
            if (p1 == "content") {
                notifyPropertyChanged(BR.content)
            }
            if (p1 == "favorite") {
                notifyPropertyChanged(BR.favorite)
            }
            if (p1 == "owner") {
                notifyPropertyChanged(BR.owner)
            }
        }
    }
}
