package com.abdulrahman.nytimes.main.ui.details


import android.os.Bundle
import com.abdulrahman.nytimes.R
import com.abdulrahman.nytimes.main.data.model.Result
import com.abdulrahman.nytimes.main.ui.bases.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

class ArticleDetailsFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_details

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        arguments?.let {
            val article = it.getParcelable<Result>(ARTICLE)
            if (article != null) {

                if (article.media.isNotEmpty()) {
                    Picasso.get()
                        .load(article.media[0].mediaMetadata[2].url)
                        .into(image_view)
                }
                tv_title.text = article.title
                tv_abstract.text = article.abstract
                tv_by_line.text = article.byline
                tv_section.text = article.section
            } else {
                navigationController.popBackStack()
            }
        } ?: run {
            navigationController.popBackStack()
        }
    }

    companion object {
        val ARTICLE = "article"
    }
}
