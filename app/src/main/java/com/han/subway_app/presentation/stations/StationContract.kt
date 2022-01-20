package com.han.subway_app.presentation.stations

import com.han.subway_app.domain.Station
import com.han.subway_app.presentation.BasePresenter
import com.han.subway_app.presentation.BaseView

interface StationContract {
    interface View : BaseView<Presenter> {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showStations(stations: List<Station>)
    }

    interface Presenter: BasePresenter{
        fun filterStations(query: String)
        fun toggleStationFavorite(station: Station)
    }
}