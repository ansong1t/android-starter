package com.dyippay.appmodules

import com.dyippay.data.qualifiers.LastUserVisitFormatter
import com.dyippay.util.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.text.SimpleDateFormat
import java.util.Locale

@InstallIn(ActivityComponent::class)
@Module
object AppModules {

    @Provides
    fun provideAppCoroutineDispatchers() = AppCoroutineDispatchers()

    @Provides
    @LastUserVisitFormatter
    fun provideSimpleDateFormatter() = SimpleDateFormat("MMM dd, yyyy hh:mm:ss", Locale.US)

    @Provides
    fun provideSimpleDateFormatterMonthYear() = SimpleDateFormat("MMM yyyy", Locale.US)
}
