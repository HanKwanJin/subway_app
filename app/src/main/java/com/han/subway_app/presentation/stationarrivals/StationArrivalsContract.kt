package com.han.subway_app.presentation.stationarrivals

import com.han.subway_app.domain.ArrivalInformation
import com.han.subway_app.presentation.BasePresenter
import com.han.subway_app.presentation.BaseView


interface StationArrivalsContract {
    interface View: BaseView<Presenter>{
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showStationArrivals(arrivalInformation: List<ArrivalInformation>)
    }

    interface Presenter: BasePresenter{
        fun fetchStationArrivals()

        fun toggleStationFavorite()
    }

}