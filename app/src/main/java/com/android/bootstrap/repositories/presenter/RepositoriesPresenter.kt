package com.android.bootstrap.repositories.presenter

import com.android.bootstrap.repositories.view.RepositoryView
import com.android.bootstrap.usecase.domain.GetRepositoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoriesPresenter(private val view: RepositoryView, private val getRepositoriesUseCase: GetRepositoriesUseCase) {
  companion object {
    private val ITEMS_PER_PAGE = 10
    private val DEFAULT_PAGE = 1
  }

  private val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }
  fun onRepositoriesLoaded() {
    getRepositories(DEFAULT_PAGE)
  }

  fun onNewPageLoaded(page: Int) {
    getRepositories(page)
  }

  private fun getRepositories(page: Int) {
    view.showLoading()
    val subscription = getRepositoriesUseCase.getRepositories(page, ITEMS_PER_PAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
              view.addRepositoriesToList(it)
              if (it.size < 10) view.stopSearching()
              view.hideLoading()
            },
            { view.showError() }
        )
    subscriptions.add(subscription)
  }

  fun onDestroy() {
    subscriptions.clear()
  }
}