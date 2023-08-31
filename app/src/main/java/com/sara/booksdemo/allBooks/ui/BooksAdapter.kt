package com.sara.booksdemo.allBooks.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sara.booksdemo.R
import com.sara.booksdemo.allBooks.pojo.BookItem


class BooksAdapter  constructor (

     val context: Context,
     private var books: List<BookItem>
    ) :

    BaseAdapter() {
        private var layoutInflater: LayoutInflater? = null
        private lateinit var imageView: ImageView
        private lateinit var textView: TextView

        override fun getCount(): Int {
            return books.size
        }
        override fun getItem(position: Int): Any? {
            return books.get(position)
        }
        override fun getItemId(position: Int): Long {
            return 0
        }
        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {


var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.book_item, null)
        }
        imageView = convertView!!.findViewById(R.id.cover_img)
        textView = convertView.findViewById(R.id.title_tv)
            textView.text = books[position].title
            Glide.with(context).load(books[position].formats.image).diskCacheStrategy(
                DiskCacheStrategy.ALL).thumbnail(0.5f).into(imageView)

          //  Glide.with(context).load(books[position].formats.image).into(imageView)

            return convertView
    }

    fun changeModelList(booksList: List<BookItem>) {
        this.books = booksList
        notifyDataSetChanged()
    }
}