package com.han.subway_app.data.repository

import com.han.subway_app.domain.ArrivalInformation
import com.han.subway_app.domain.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    val stations: Flow<List<Station>>

    suspend fun refreshStations()
    suspend fun getStationArrivals(stationName: String): List<ArrivalInformation>
    suspend fun updateStation(station: Station)
}