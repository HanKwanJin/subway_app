package com.han.subway_app.data.db.entity

import androidx.room.*

@Entity
data class StationEntity(
    @PrimaryKey val stationName: String,
    val isFavorite: Boolean = false
)
