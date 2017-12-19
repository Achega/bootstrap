package com.android.bootstrap.usecase

import com.android.bootstrap.usecase.data.api.GitHubApi
import com.android.bootstrap.usecase.data.RepoRepository
import com.android.bootstrap.usecase.domain.GetRepositoriesUseCase
import com.android.bootstrap.usecase.domain.model.mapper.RepoDataToDomainMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetRepositoriesUseCaseServiceLocator {
  fun provideGetRepositoriesUseCase() = GetRepositoriesUseCase(provideRepoRepository())

  private fun provideRepoRepository() = RepoRepository(provideGitHubApi(), RepoDataToDomainMapper())

  private fun provideGitHubApi() = GitHubApi(provideRetrofit())

  private fun provideRetrofit() =
      Retrofit.Builder()
          .baseUrl("https://api.github.com/")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()
}