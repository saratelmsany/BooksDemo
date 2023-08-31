package com.sara.booksdemo.bookDetails.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sara.booksdemo.R
import com.sara.booksdemo.allBooks.pojo.BookItem

class RelatedAdapter (
    val context: Context,
    private val books: List<BookItem>,

    ) : RecyclerView.Adapter<RelatedAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        view.layoutParams = ViewGroup.LayoutParams((parent.width * 0.5).toInt(),ViewGroup.LayoutParams.MATCH_PARENT)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val itemsViewModel = books[position]

            Glide.with(context).load(itemsViewModel.formats.image).diskCacheStrategy(
                DiskCacheStrategy.ALL
            ).thumbnail(0.5f).into(holder.imageView)

            holder.textView.text = itemsViewModel.title

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("book", books[position])
            context.startActivity(intent)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return books.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cover_img)
        val textView: TextView = itemView.findViewById(R.id.title_tv)
    }
}