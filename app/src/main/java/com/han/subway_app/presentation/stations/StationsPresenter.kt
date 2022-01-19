package com.han.subway_app.presentation.stations

import com.han.subway_app.data.repository.StationRepository
import com.han.subway_app.domain.Station
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StationsPresenter(
    private val view: StationContract.View,
    private val stationRepository: StationRepository
): StationContract.Presenter {

    override val scope: CoroutineScope = MainScope()
    private val queryString: MutableStateFlow<String> = MutableStateFlow("")
    private val stations: MutableStateFlow<List<Station>> = MutableStateFlow(emptyList())
    init {
        observeStations()
    }
    override fun onViewCreated() {
        scope.launch {
            view.showStations(stations.value)
            stationRepository.refreshStations()
        }
    }
    override fun filterStations(query: String) {
        scope.launch {
            queryString.emit(query)
        }
    }
    override fun onDestroyView() {}

    private fun observeStations(){
        stationRepository
            .stations
            .combine(queryString){stations, query ->
                if(query.isBlank()){
                    stations
                }else {
                    stations.filter { it.name.contains(query) }
                }
            }
            .onStart { view.showLoadingIndicator() }
            .onEach {
                if(it.isNotEmpty()){
                    view.hideLoadingIndicator()
                }
                stations.value = it
                view.showStations(it)
            }
            .catch {
                it.printStackTrace()
                view.hideLoadingIndicator()
            }
            .launchIn(scope)
    }

}