package com.lukianbat.urbanist.guide.feature.map.di

import com.lukianbat.urbanist.guide.feature.list.data.repository.MapRepository
import com.lukianbat.urbanist.guide.feature.map.data.CacheRouteDao
import com.lukianbat.urbanist.guide.feature.map.domain.repository.MapRepositoryImpl
import com.lukianbat.urbanist.guide.feature.map.data.MapApi
import com.lukianbat.urbanist.guide.сore.dagger.scope.ActivityScope
import com.lukianbat.urbanist.guide.сore.data.AppDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class MapNetworkModule {

    @Provides
    fun provideCashRouteDao(appDatabase: AppDatabase): CacheRouteDao {
        return appDatabase.cashRouteDao()
    }

    @Provides
    @ActivityScope
    fun provideApi(@Named("graphhopper") retrofit: Retrofit): MapApi =
        retrofit.create(MapApi::class.java)

    @Provides
    @ActivityScope
    fun provideRepository(mapApi: MapApi, dao: CacheRouteDao): MapRepository =
        MapRepositoryImpl(mapApi, dao)
}