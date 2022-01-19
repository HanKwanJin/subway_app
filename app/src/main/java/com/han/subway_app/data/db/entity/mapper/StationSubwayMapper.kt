package com.han.subway_app.data.db.entity.mapper

import com.han.subway_app.data.db.entity.StationEntity
import com.han.subway_app.data.db.entity.StationWithSubwayEntity
import com.han.subway_app.data.db.entity.SubwayEntity
import com.han.subway_app.domain.Station
import com.han.subway_app.domain.Subway

fun StationWithSubwayEntity.toStation() = Station(
    name = station.stationName,
    isFavorite = station.isFavorite,
    connectedSubways = subways.toSubways()
)

fun Station.toStationEntity() =
    StationEntity(
        stationName = name,
        isFavorite = isFavorite,
    )


fun List<StationWithSubwayEntity>.toStations() = map { it.toStation() }

fun List<SubwayEntity>.toSubways(): List<Subway> = map {Subway.findById(it.subwayId)}