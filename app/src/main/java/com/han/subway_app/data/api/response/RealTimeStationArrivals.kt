package com.han.subway_app.data.api.response

import com.google.gson.annotations.SerializedName

data class RealTimeStationArrivals(
    @SerializedName("errorMessage")
    val errorMessage: ErrorMessage? = null,
    @SerializedName("realtimeArrivalList")
    val realtimeArrivalList: List<RealtimeArrival>? = null
)
