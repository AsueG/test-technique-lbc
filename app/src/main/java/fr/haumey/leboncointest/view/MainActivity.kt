package fr.haumey.leboncointest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.haumey.leboncointest.R
import fr.haumey.leboncointest.view.listing.ListingFragment
import fr.haumey.leboncointest.view.listing.ListingFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}