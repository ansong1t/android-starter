package com.dyippay.appinitializers

import android.app.Application
import com.dyippay.util.AppCoroutineDispatchers
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.zone.ZoneRulesProvider
import javax.inject.Inject

class ThreeTenBpInitializer @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers
) : AppInitializer {
    override fun init(application: Application) {
        // Init ThreeTenABP
        AndroidThreeTen.init(application)

        // Query the ZoneRulesProvider so that it is loaded on a background coroutine
        GlobalScope.launch(dispatchers.io) {
            ZoneRulesProvider.getAvailableZoneIds()
        }
    }
}
