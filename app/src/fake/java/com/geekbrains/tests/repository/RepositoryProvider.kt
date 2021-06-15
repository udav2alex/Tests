package com.geekbrains.tests.repository

import com.geekbrains.tests.presenter.RepositoryContract

class RepositoryProvider {
    companion object {
        const val TEST_DELAY = 1L

        internal fun createRepository(): RepositoryContract =
            FakeGitHubRepository()
    }
}