package com.han.subway_app.di

import android.app.Activity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.han.subway_app.data.api.StationApi
import com.han.subway_app.data.api.StationStorageApi
import com.han.subway_app.data.db.AppDatabase
import com.han.subway_app.data.preference.PreferenceManager
import com.han.subway_app.data.preference.SharedPreferenceManager
import com.han.subway_app.data.repository.StationRepository
import com.han.subway_app.data.repository.StationRepositoryImpl
import com.han.subway_app.presentation.stations.StationContract
import com.han.subway_app.presentation.stations.StationsFragment
import com.han.subway_app.presentation.stations.StationsPresenter
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { Dispatchers.IO }

    //Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().stationDao() }

    //Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager>{SharedPreferenceManager(get())}

    //Api
    single<StationApi> {StationStorageApi(Firebase.storage)}

    //Repository
    single<StationRepository> { StationRepositoryImpl(get(), get(), get(), get())}

    //Presentation
    scope<StationsFragment> {
        scoped<StationContract.Presenter> {
            StationsPresenter(getSource(), get())
        }
    }
}