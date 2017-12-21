package com.android.bootstrap

import com.android.bootstrap.repositories.presenter.ReposPresenter
import com.android.bootstrap.repositories.view.ReposView
import com.android.bootstrap.usecase.domain.GetReposUseCase
import com.android.bootstrap.usecase.domain.model.Repo
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mock

import org.mockito.junit.MockitoJUnit

class ReposPresenterShould {

  companion object {
    private val ANY_REPO = Repo("anyName", "anyDescription", "anyLogin", true, "anyUrl", "anyOwnerUrl")
    private val LIST_WITH_FULL_PAGE = listOf<Repo>(ANY_REPO, ANY_REPO, ANY_REPO, ANY_REPO, ANY_REPO, ANY_REPO, ANY_REPO, ANY_REPO,
        ANY_REPO, ANY_REPO)
    private val LIST_WITHOUT_FULL_PAGE = listOf<Repo>(ANY_REPO, ANY_REPO)
    private val ANY_PAGE = 1
    private val ANY_ELEMENTS_PER_PAGE = 10
  }

  @get:Rule
  val mockitoRule = MockitoJUnit.rule()

  @Mock private lateinit var getReposUseCase: GetReposUseCase

  @Mock private lateinit var view: ReposView

  private lateinit var presenter: ReposPresenter

  @Before
  fun setUp() {
    presenter = ReposPresenter(view, getReposUseCase, Schedulers.trampoline(), Schedulers.trampoline())
  }

  @Test
  fun show_repositories_when_view_is_open() {
    given(getReposUseCase.getRepositories(ANY_PAGE, ANY_ELEMENTS_PER_PAGE))
        .willReturn(Single.fromCallable { LIST_WITH_FULL_PAGE })

    presenter.onRepositoriesLoaded()

    verify<ReposView>(view).showLoading()
    verify<ReposView>(view).addRepositoriesToList(LIST_WITH_FULL_PAGE)
    verify<ReposView>(view).hideLoading()
  }

  @Test
  fun add_more_repositories_and_continue_searching() {
    given(getReposUseCase.getRepositories(ANY_PAGE, ANY_ELEMENTS_PER_PAGE))
        .willReturn(Single.fromCallable { LIST_WITH_FULL_PAGE })

    presenter.onNewPageLoaded(ANY_PAGE)

    verify<ReposView>(view).showLoading()
    verify<ReposView>(view).addRepositoriesToList(LIST_WITH_FULL_PAGE)
    verify<ReposView>(view).hideLoading()
  }

  @Test
  fun add_more_repositories_and_and_stop_searching_if_there_are_no_more_repos() {
    given(getReposUseCase.getRepositories(ANY_PAGE, ANY_ELEMENTS_PER_PAGE))
        .willReturn(Single.fromCallable { LIST_WITHOUT_FULL_PAGE })

    presenter.onNewPageLoaded(ANY_PAGE)

    verify<ReposView>(view).showLoading()
    verify<ReposView>(view).addRepositoriesToList(LIST_WITHOUT_FULL_PAGE)
    verify<ReposView>(view).stopSearching()
    verify<ReposView>(view).hideLoading()
  }

  @Test
  fun show_an_error_message_if_there_is_an_error() {
    given(getReposUseCase.getRepositories(ANY_PAGE, ANY_ELEMENTS_PER_PAGE))
        .willReturn(Single.error { Throwable() })

    presenter.onNewPageLoaded(ANY_PAGE)

    verify<ReposView>(view).showLoading()
    verify<ReposView>(view).showError()
    verify<ReposView>(view).hideLoading()
  }
}