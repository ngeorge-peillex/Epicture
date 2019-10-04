package fr.niels.epicture.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import fr.niels.epicture.BR
import fr.niels.epicture.BaseActivity
import fr.niels.epicture.R
import fr.niels.epicture.provider.UserProvider
import fr.niels.epicture.viewModel.UserViewModel

class ProfileActivity : BaseActivity(2) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userProvider = UserProvider()
        userProvider.get()

        val userViewModel = UserViewModel(userProvider.user)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_profile)
        binding.setVariable(BR.user, userViewModel)

        setupBottomNavigation()
    }
}
