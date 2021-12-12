package app.placeeventmap.server.retrofit

import app.placeeventmap.server.models.ProfileInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface YandexOauthApi {
    @GET("info")
    suspend fun getProfileInfo(
        @Header("Authorization") auth: String,
    ) : Response<ProfileInfo>
}