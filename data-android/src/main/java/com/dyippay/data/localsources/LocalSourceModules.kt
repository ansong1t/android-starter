package com.dyippay.data.localsources

import com.dyippay.data.localsources.session.SessionLocalSource
import com.dyippay.data.localsources.session.SessionLocalSourceImpl
import com.dyippay.data.localsources.token.AccessTokenLocalSource
import com.dyippay.data.localsources.token.AccessTokenLocalSourceImpl
import com.dyippay.data.localsources.user.UserLocalSource
import com.dyippay.data.localsources.user.UserLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalSourceModules {

    @Binds
    @Singleton
    abstract fun bindSessionLocalSource(repository: SessionLocalSourceImpl): SessionLocalSource

    @Binds
    @Singleton
    abstract fun bindUserLocalSource(repository: UserLocalSourceImpl): UserLocalSource

    @Binds
    @Singleton
    abstract fun bindUAccessTokenLocalSource(repository: AccessTokenLocalSourceImpl): AccessTokenLocalSource
}
