package com.geekbrains.tests

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.repository.RepositoryProvider
import com.geekbrains.tests.view.search.MainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchEditText_TotalVisibilityTest() {
        view_TotalVisibilityTest(R.id.searchEditText)
    }

    @Test
    fun toDetailsActivityButton_TotalVisibilityTest() {
        view_TotalVisibilityTest(R.id.toDetailsActivityButton)
    }

    private fun view_TotalVisibilityTest(viewId: Int) {
        with(viewId) {
            onView(withId(this)).check(matches(isDisplayed()))
            onView(withId(this)).check(matches(isCompletelyDisplayed()))
            onView(withId(this)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        }
    }

    @Test
    fun toDetailsActivityButton_AvailabilityTest() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isEnabled()))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isClickable()))
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        onView(isRoot()).perform(delay())
        onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 2422")))

        view_TotalVisibilityTest(R.id.totalCountTextView)
    }

    private fun delay() = object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()

        val delay = RepositoryProvider.TEST_DELAY
        override fun getDescription(): String = "wait for ${delay / 1000} seconds"

        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}
