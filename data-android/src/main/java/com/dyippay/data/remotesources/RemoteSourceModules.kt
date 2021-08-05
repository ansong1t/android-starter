package com.dyippay.data.remotesources

import com.dyippay.data.remotesources.auth.AuthRemoteSource
import com.dyippay.data.remotesources.auth.AuthRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteSourceModules {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteSource(repository: AuthRemoteSourceImpl): AuthRemoteSource
}
