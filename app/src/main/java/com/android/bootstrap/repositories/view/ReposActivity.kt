package com.android.bootstrap.repositories.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View.VISIBLE
import android.view.View.GONE
import com.android.bootstrap.R
import com.android.bootstrap.usecase.domain.model.Repo
import com.android.bootstrap.repositories.presenter.ReposPresenter
import com.android.bootstrap.repositories.view.adapter.ReposAdapter
import com.android.bootstrap.repositories.view.adapter.ScrollListener
import com.android.bootstrap.usecase.GetReposUseCaseServiceLocator
import kotlinx.android.synthetic.main.activity_repositories.repositoriesRecyclerView
import kotlinx.android.synthetic.main.progress_bar_view.progress_bar
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class ReposActivity : AppCompatActivity(), ReposView {
  private val presenter = ReposPresenter(this, GetReposUseCaseServiceLocator.provideGetRepositoriesUseCase())
  private val layoutManager = LinearLayoutManager(this)
  private var isLoading = false
  private var isLastPage = false
  private var currentPage = 1
  private lateinit var adapter: ReposAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_repositories)
    initAdapter()
    initRecyclerView()
    presenter.onRepositoriesLoaded()
  }

  private fun initAdapter() {
    adapter = ReposAdapter() { repository ->
      alert(R.string.alert_message) {
        positiveButton(R.string.alert_positive_button) { openInBrowser(repository.htmlUrl) }
        negativeButton(R.string.alert_negative_button) { openInBrowser(repository.ownerHtmlUrl) }
      }.show()
    }
  }

  private fun initRecyclerView() {
    repositoriesRecyclerView.layoutManager = layoutManager
    repositoriesRecyclerView.adapter = adapter
    repositoriesRecyclerView.addOnScrollListener(object : ScrollListener(layoutManager) {
      override fun loadMoreItems() {
        isLoading = true
        currentPage++
        presenter.onNewPageLoaded(currentPage)
      }

      override fun isLastPage(): Boolean = isLastPage
      override fun isLoading(): Boolean = isLoading
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun addRepositoriesToList(repositories: List<Repo>) {
    isLoading = false
    adapter.addRepositories(repositories)
  }

  override fun stopSearching() {
    isLastPage = true
  }

  override fun showLoading() {
    progress_bar.visibility = VISIBLE
  }

  override fun hideLoading() {
    progress_bar.visibility = GONE
  }

  override fun showError() {
    toast(R.string.error_message)
  }

  private fun openInBrowser(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
  }
}