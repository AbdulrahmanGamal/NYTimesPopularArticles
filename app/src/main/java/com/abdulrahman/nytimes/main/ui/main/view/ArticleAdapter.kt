package com.abdulrahman.nytimes.main.ui.main.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.nytimes.R
import com.abdulrahman.nytimes.main.data.model.Result
import com.abdulrahman.nytimes.main.ui.bases.OnListItemClickListener
import com.abdulrahman.nytimes.main.utilities.PicassoCircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter(
    private val context: Context,
    private val results: List<Result>,
    private val onListItemClickListener: OnListItemClickListener<Result>
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        )

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(p0: View?) {
            onListItemClickListener.onItemClick(itemView, results[adapterPosition])
        }

        fun onBind(position: Int) {
            itemView.setOnClickListener(this)
            val article = results[position]
            itemView.tv_text.text = article.abstract
            itemView.tv_owner.text = article.byline
            itemView.tv_date.text = article.publishedDate
            if (article.media.isNotEmpty()) {
                Picasso.get()
                    .load(article.media[0].mediaMetadata[0].url)
                    .transform(PicassoCircleTransformation())
                    .into(itemView.image_view)
            }
        }
    }
}