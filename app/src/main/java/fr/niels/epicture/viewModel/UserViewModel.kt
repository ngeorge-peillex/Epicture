package fr.niels.epicture.viewModel

import fr.niels.epicture.model.User
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import fr.niels.epicture.BR
import java.util.*

class UserViewModel(private val user: User) : Observer, BaseObservable() {
    /// Register itself as the observer of Model
    init {
        user.addObserver(this)
    }

    val username: String
        @Bindable get() {
            return user.username
        }

    val bio: String
        @Bindable get() {
            return user.bio
        }

    /// Notify the UI when change event emitting from Model is received.
    override fun update(p0: Observable?, p1: Any?) {
        if (p1 is String) {
            if (p1 == "username") {
                notifyPropertyChanged(BR.username)
            }
            if (p1 == "bio") {
                notifyPropertyChanged(BR.bio)
            }
        }
    }
}
