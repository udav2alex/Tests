package com.geekbrains.tests.presenter

import com.geekbrains.tests.view.ViewContract

internal interface PresenterContract<V> {
    fun onAttach(view: V)
    fun onDetach()
}
