package com.han.subway_app.data.repository

import com.han.subway_app.data.api.StationApi
import com.han.subway_app.data.api.StationArrivalsApi
import com.han.subway_app.data.api.response.mapper.toArrivalInformation
import com.han.subway_app.data.db.StationDao
import com.han.subway_app.data.db.entity.mapper.toStationEntity
import com.han.subway_app.data.db.entity.mapper.toStations
import com.han.subway_app.data.preference.PreferenceManager
import com.han.subway_app.domain.ArrivalInformation
import com.han.subway_app.domain.Station
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StationRepositoryImpl(
    private val stationArrivalsApi: StationArrivalsApi,
    private val stationApi: StationApi,
    private val stationDao: StationDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher
): StationRepository {
    override val stations: Flow<List<Station>> =
        stationDao.getStationWithSubways()
            .distinctUntilChanged()
            .map { stations -> stations.toStations().sortedByDescending { it.isFavorite } }
            .flowOn(dispatcher)

    override suspend fun refreshStations() = withContext(dispatcher){
        val fileUpdatedTimeMillis = stationApi.getStationDataUpdatedTimeMillis()
        val lastDatabaseUpdatedTimeMillis = preferenceManager.getLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS)

        if (lastDatabaseUpdatedTimeMillis == null || fileUpdatedTimeMillis > lastDatabaseUpdatedTimeMillis) {
            stationDao.insertStationSubways(stationApi.getStationSubways())
            preferenceManager.putLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS, fileUpdatedTimeMillis)
        }
    }

    override suspend fun getStationArrivals(stationName: String): List<ArrivalInformation> = withContext(dispatcher){
        stationArrivalsApi.getRealtimeStationArrivals(stationName)
            .body()
            ?.realtimeArrivalList
            ?.toArrivalInformation()
            ?.distinctBy { it.direction }
            ?.sortedBy { it.subway }
            ?: throw RuntimeException("도착 정보를 불러오는 데에 실패했습니다.")

    }

    override suspend fun updateStation(station: Station) =
        withContext(dispatcher){
            stationDao.updateStation(station.toStationEntity())
        }

    companion object{
        private const val KEY_LAST_DATABASE_UPDATED_TIME_MILLIS = "KEY_LAST_DATABASE_UPDATED_TIME_MILLIS"
    }
}