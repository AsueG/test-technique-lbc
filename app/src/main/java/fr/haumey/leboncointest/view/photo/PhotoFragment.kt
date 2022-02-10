package fr.haumey.leboncointest.view.photo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import fr.haumey.leboncointest.R

@AndroidEntryPoint
class PhotoFragment : Fragment(R.layout.fragment_photo) {

    private val args: PhotoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get()
            .load(args.url)
            .into(view.findViewById<ImageView>(R.id.imageViewBig))
    }
}