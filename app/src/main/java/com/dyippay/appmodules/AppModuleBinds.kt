package com.dyippay.appmodules

import com.dyippay.appinitializers.AppInitializer
import com.dyippay.appinitializers.ThreeTenBpInitializer
import com.dyippay.appinitializers.TimberInitializer
import com.dyippay.util.DyippayLogger
import com.dyippay.util.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun provideLogger(bind: DyippayLogger): Logger

    @Binds
    @IntoSet
    abstract fun provideThreeTenAbpInitializer(bind: ThreeTenBpInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): AppInitializer
}
