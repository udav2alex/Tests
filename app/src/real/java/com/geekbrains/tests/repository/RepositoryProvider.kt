package com.geekbrains.tests.repository

import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.view.search.MainActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryProvider {
    companion object {
        const val TEST_DELAY = 10000L

        internal fun createRepository(): RepositoryContract =
            GitHubRepository(createRetrofit().create(GitHubApi::class.java))

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}