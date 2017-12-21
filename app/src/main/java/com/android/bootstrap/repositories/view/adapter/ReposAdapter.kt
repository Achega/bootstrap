package com.android.bootstrap.repositories.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.bootstrap.R
import com.android.bootstrap.usecase.domain.model.Repo

class ReposAdapter(private val onItemClick: (Repo) -> Unit) : RecyclerView.Adapter<RepoViewHolder>() {
  private var repositories: MutableList<Repo> = mutableListOf()

  override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
    holder.bind(repositories[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_view, parent, false)
    return RepoViewHolder(view, onItemClick)
  }

  override fun getItemCount() = repositories.size

  fun addRepositories(newRepositories: List<Repo>) {
    newRepositories.forEach { addRepository(it) }
  }

  private fun addRepository(repository: Repo) {
    repositories.add(repository)
    notifyItemInserted(repositories.size - 1)
  }
}