package be.appwise.core.core

import android.content.Context
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class CoreBuilder(internal val context: Context) {
    private var initializedErrorActivity = false

    /***
     * Initialize Hawk, This way you don't need to add the dependency to the 'real' application/project
     */
    internal fun initializeHawk(): CoreBuilder {
        Hawk.init(context)
            .build()

        return this
    }

    /***
     * Initialize this logger
     *
     * @param tag The name to be used in LogCat
     * @param isLoggable Whether or not the logger should be enabled for this application.
     *      When using in a production/release version it should not be enabled (security reasons) i.e. use BuildConfig.DEBUG
     */
    fun initializeLogger(tag: String, isLoggable: Boolean): CoreBuilder {
        val formatStrategy = PrettyFormatStrategy.newBuilder().tag(tag).build() /*Set a tag*/

        // Initialize Logger
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                // This makes sure the logs only show on debug builds, it is the preferred way and safest
                return isLoggable
            }
        })

        return this
    }

    fun initializeErrorActivity(showErrorDetails: Boolean = false): CoreBuilder{
        initializedErrorActivity = true

        CaocConfig.Builder.create()
            .enabled(true)
            .showErrorDetails(showErrorDetails)
            .apply()

        return this
    }

    fun build() {
        if (!initializedErrorActivity){
            CaocConfig.Builder.create()
                .enabled(false)
                .apply()
        }

        CoreApp.build(this)
    }
}