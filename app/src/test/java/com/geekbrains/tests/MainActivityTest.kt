package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_NotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activityState_Resumed() {
        scenario.onActivity {
            assertEquals(scenario.state, Lifecycle.State.RESUMED)
        }
    }

    @Test
    fun activityEditText_NotNull() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            assertNotNull(editText)
        }
    }

    @Test
    fun activityEditText_Working() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            editText.setText("text")
            assertNotNull(editText.text)
            assertEquals("text", editText.text.toString())
        }
    }

    @Test
    fun activityEditText_IsVisible() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            TestCase.assertEquals(View.VISIBLE, editText.visibility)
        }
    }

    @Test
    fun activityEditText_QueryListener_IfBlank() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            editText.setText("")
            editText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
            ShadowLooper.idleMainLooper()
            assertThat(
                ShadowToast.getTextOfLatestToast().toString(),
                equalTo(it.getString(R.string.enter_search_word)))
        }
    }
}