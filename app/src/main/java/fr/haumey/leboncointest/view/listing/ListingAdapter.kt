package fr.haumey.leboncointest.view.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.haumey.leboncointest.R
import fr.haumey.leboncointest.model.Title

class ListingAdapter(private val listener: (Title) -> Unit) : RecyclerView.Adapter<ListingAdapter.ListingHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Title>() {
        override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class ListingHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_title, parent, false)) {

        private val thumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
        private val titleName: TextView = itemView.findViewById(R.id.textViewTitleName)

        fun bind (title: Title) {
            titleName.text = title.title

            Picasso.get()
                .load(title.thumbnailUrl)
                .into(thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ListingHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ListingHolder, position: Int) {
        val title = differ.currentList[position]
        holder.bind(title)

        holder.itemView.setOnClickListener {
            listener(title)
        }
    }

    override fun getItemCount() = differ.currentList.size
}