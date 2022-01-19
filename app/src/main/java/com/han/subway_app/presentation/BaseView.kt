package com.han.subway_app.presentation

interface BaseView<PresenterT : BasePresenter> {
    val presenter: PresenterT
}