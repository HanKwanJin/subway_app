package com.han.subway_app.data.api

import com.han.subway_app.BuildConfig
import com.han.subway_app.data.api.response.RealTimeStationArrivals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StationArrivalsApi {
    @GET("api/subway/${BuildConfig.SEOUL_API_ACCESS_KEY}/json/realtimeStationArrival/0/100/{stationName}")
    suspend fun getRealtimeStationArrivals(@Path("stationName") stationName: String): Response<RealTimeStationArrivals>
}