package com.android.bootstrap.repositories.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.android.bootstrap.R
import com.android.bootstrap.usecase.domain.model.Repo
import com.android.bootstrap.repositories.presenter.RepositoriesPresenter
import com.android.bootstrap.repositories.view.adapter.RepositoryAdapter
import com.android.bootstrap.repositories.view.adapter.ScrollListener
import com.android.bootstrap.usecase.GetRepositoriesUseCaseServiceLocator
import kotlinx.android.synthetic.main.activity_repositories.repositoriesRecyclerView
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class RepositoriesActivity : AppCompatActivity(), RepositoryView {
  private val presenter = RepositoriesPresenter(this, GetRepositoriesUseCaseServiceLocator.provideGetRepositoriesUseCase())
  private val layoutManager = LinearLayoutManager(this)
  private var isLoading = false
  private var isLastPage = false
  private var currentPage = 1
  private lateinit var adapter: RepositoryAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_repositories)
    initAdapter()
    initRecyclerView()
    presenter.onRepositoriesLoaded()
  }

  private fun initAdapter() {
    adapter = RepositoryAdapter() { repository ->
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

  override fun showRepositories(repositories: List<Repo>) {
    adapter.addRepositories(repositories)
  }

  override fun addRepositoriesToList(repositories: List<Repo>) {
    isLoading = false
    adapter.addRepositories(repositories)
  }

  override fun stopSearching() {
    isLastPage = true
  }

  override fun showError() {
    toast(R.string.error_message)
  }

  private fun openInBrowser(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
  }
}