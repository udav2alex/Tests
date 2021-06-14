package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.verifyNoMoreInteractions
import kotlin.random.Random

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var view: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter()
        presenter.onAttach(view)
    }

    @Test
    fun setCounter_Test() {
        val value = Random.nextInt(500) - 1000
        presenter.setCounter(value)

        verify(view).setCount(value)
    }

    @Test
    fun onDecrement_Test() {
        val value = Random.nextInt(500) - 1000
        presenter.setCounter(value)
        verify(view).setCount(value)
        presenter.onDecrement()
        verify(view).setCount(value - 1)
    }

    @Test
    fun onIncrement_Test() {
        val value = Random.nextInt(500) - 1000
        presenter.setCounter(value)
        verify(view).setCount(value)
        presenter.onIncrement()
        verify(view).setCount(value + 1)
    }

    @Test
    fun onAttach_Test() {
        val newView = Mockito.mock(ViewDetailsContract::class.java)
        presenter.onAttach(newView)

        assertEquals(newView, presenter.viewContract)
        verify(view).setCount(0)
    }

    @Test
    fun onDetach_Test() {
        val newView = Mockito.mock(ViewDetailsContract::class.java)
        presenter.onAttach(newView)
        presenter.onDetach()

        assertNull(presenter.viewContract)
    }
}