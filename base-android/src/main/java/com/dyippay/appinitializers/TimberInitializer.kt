package com.dyippay.appinitializers

import android.app.Application
import com.dyippay.util.DyippayLogger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val qrLogger: DyippayLogger
) : AppInitializer {
    override fun init(application: Application) = qrLogger.setup()
}
