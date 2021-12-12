package app.placeeventmap.server.retrofit

import app.placeeventmap.server.models.ProfileInfo
import retrofit2.Response

object Common {
    private const val ID_BASE_URL = "https://login.yandex.ru/"
    private val authService: YandexOauthApi
        get() = RetrofitClient.getClient(ID_BASE_URL).create(YandexOauthApi::class.java)

    suspend fun getProfileInfo(access_code: String = ""): Response<ProfileInfo> {
        //        var authResponse: ProfileInfo? = null
//        authResponse = (response.body() as ProfileInfo)
        return authService.getProfileInfo(auth = "OAuth $access_code")
    }
}