package com.han.subway_app.data.api

import com.han.subway_app.data.db.entity.StationEntity
import com.han.subway_app.data.db.entity.SubwayEntity

interface StationApi {
    suspend fun getStationDataUpdatedTimeMillis(): Long

    suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>>
}