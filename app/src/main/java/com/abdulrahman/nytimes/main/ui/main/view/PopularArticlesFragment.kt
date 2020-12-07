package com.abdulrahman.nytimes.main.ui.main.view

import android.os.Bundle
import android.view.View
import com.abdulrahman.nytimes.R
import com.abdulrahman.nytimes.main.data.model.Result
import com.abdulrahman.nytimes.main.ui.bases.BaseFragment
import com.abdulrahman.nytimes.main.ui.bases.OnListItemClickListener
import com.abdulrahman.nytimes.main.ui.bases.StateData
import com.abdulrahman.nytimes.main.ui.details.ArticleDetailsFragment
import com.abdulrahman.nytimes.main.ui.main.viewModel.PopularArticlesViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class PopularArticlesFragment : BaseFragment(), OnListItemClickListener<Result>, KoinComponent {
    override val layoutId: Int
        get() = R.layout.main_fragment


    private val viewModel: PopularArticlesViewModel by inject()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeAndShowArticles()
        sr_refresh.setOnRefreshListener {
            observeAndShowArticles()
        }
    }

    private fun observeAndShowArticles() {
        sr_refresh.isRefreshing = true

        viewModel.getAllArticles().observe(requireActivity(), {
            when (it.getStatus()) {

                StateData.DataStatus.LOADING -> {
                    fl_container.visibility = View.GONE
                    showLayoutLoading()
                }

                StateData.DataStatus.SUCCESS -> {
                    sr_refresh.isRefreshing = false
                    fl_container.visibility = View.VISIBLE
                    hideLayoutErrorAndLoading()
                    it?.getData()?.let { res ->
                        rv_articles.adapter = ArticleAdapter(requireContext(), res.results, this)
                    } ?: run {
                        showLayoutError("No Data found in list")
                    }
                }

                StateData.DataStatus.ERROR -> {
                    fl_container.visibility = View.GONE
                    showLayoutError(it.getError()?.message.toString())
                }

                StateData.DataStatus.NOT_COMPLETED -> {
                    fl_container.visibility = View.GONE
                    showLayoutError("Something wrong happened")
                }
                StateData.DataStatus.NO_INTERNET -> {
                    fl_container.visibility = View.GONE
                    showLayoutError("No Internet Connection")
                }
            }
        })
    }

    override fun onItemClick(view: View, model: Result) {
        val bundle = Bundle()
        bundle.putParcelable(ArticleDetailsFragment.ARTICLE, model)
        navigationController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }
}
