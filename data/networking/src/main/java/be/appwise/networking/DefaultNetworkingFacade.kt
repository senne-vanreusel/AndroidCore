package be.appwise.networking

import be.appwise.networking.base.BaseNetworkingListeners
import be.appwise.networking.model.AccessToken
import be.appwise.networking.model.ApiError
import be.appwise.networking.util.HawkUtils
import retrofit2.Response

class DefaultNetworkingFacade(networkingBuilder: Networking.Builder) :
    NetworkingFacade {

    //<editor-fold desc="Variables">
    /**
     * These are all variables that are needed to make this class/library work.
     * They have to be in this order as well, else things won't compile and break.
     */
    override val appName = networkingBuilder.getAppName()
    override val versionName = networkingBuilder.getVersionName()
    override val versionCode = networkingBuilder.getVersionCode()
    override val apiVersion = networkingBuilder.getApiVersion()
    override val packageName = networkingBuilder.getPackageName()
    private val listener: BaseNetworkingListeners = networkingBuilder.getNetworkingListeners()

    override val clientId = networkingBuilder.getClientIdValue()
    override val clientSecret = networkingBuilder.getClientSecretValue()

    override val context = networkingBuilder.context
    //</editor-fold>

    override fun getAccessToken() = HawkUtils.hawkAccessToken

    override fun saveAccessToken(accessToken: AccessToken) {
        HawkUtils.hawkAccessToken = accessToken
    }

    override fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    override fun logout() {
        listener.logout()
    }

    override fun parseError(response: Response<*>): ApiError {
        return listener.parseError(response)
    }
}
