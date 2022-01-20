package com.han.subway_app.di

import android.app.Activity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.han.subway_app.BuildConfig
import com.han.subway_app.data.api.StationApi
import com.han.subway_app.data.api.StationArrivalsApi
import com.han.subway_app.data.api.StationStorageApi
import com.han.subway_app.data.api.Url
import com.han.subway_app.data.db.AppDatabase
import com.han.subway_app.data.preference.PreferenceManager
import com.han.subway_app.data.preference.SharedPreferenceManager
import com.han.subway_app.data.repository.StationRepository
import com.han.subway_app.data.repository.StationRepositoryImpl
import com.han.subway_app.presentation.stationarrivals.StationArrivalsContract
import com.han.subway_app.presentation.stationarrivals.StationArrivalsFragment
import com.han.subway_app.presentation.stationarrivals.StationArrivalsPresenter
import com.han.subway_app.presentation.stations.StationContract
import com.han.subway_app.presentation.stations.StationsFragment
import com.han.subway_app.presentation.stations.StationsPresenter
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {
    single { Dispatchers.IO }

    //API
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if(BuildConfig.DEBUG){
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<StationArrivalsApi> {
        Retrofit.Builder().baseUrl(Url.SEOUL_DATA_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    //Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().stationDao() }

    //Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager>{SharedPreferenceManager(get())}

    //Api
    single<StationApi> {StationStorageApi(Firebase.storage)}

    //Repository
    single<StationRepository> { StationRepositoryImpl(get(), get(), get(), get(), get())}

    //Presentation
    scope<StationsFragment> {
        scoped<StationContract.Presenter> {
            StationsPresenter(getSource(), get())
        }
    }
    scope<StationArrivalsFragment> {
        scoped<StationArrivalsContract.Presenter> {
            StationArrivalsPresenter(getSource(), get(), get())
        }
    }
}