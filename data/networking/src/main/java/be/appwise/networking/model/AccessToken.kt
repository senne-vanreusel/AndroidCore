package be.appwise.networking.model

import com.google.gson.annotations.Expose

open class AccessToken {
    @Expose
    var id: Int = 0
    @Expose
    var token_type: String? = null
    @Expose
    var expires_in: Long = 0
    @Expose
    var access_token: String? = null
    @Expose
    var refresh_token: String? = null
}