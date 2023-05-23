package app.placeeventmap.server.controllers

import app.placeeventmap.server.db.repositories.ProfileRepository
import app.placeeventmap.server.models.Profile
import app.placeeventmap.server.models.ProfileInfo
import app.placeeventmap.server.retrofit.Common
import app.placeeventmap.server.security.services.ProfileService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("profile")
class ProfileController(private val profileRepository: ProfileRepository) {

    private val common = Common

    @DelicateCoroutinesApi
    @GetMapping("/info")
    fun getInfoByToken(authentication: Authentication): ResponseEntity<ProfileInfo?> {
        var result: ResponseEntity<ProfileInfo?> = ResponseEntity.badRequest().body(null)

        println("AUTHENTICATION NAME: ${authentication.name}")

        val profile: Profile? = profileRepository.findByAuthTokenLike(authentication.name)

        runBlocking {
            val job = GlobalScope.launch {
                if (profile != null) {
                    val res = common.getProfileInfo(profile.authToken!!)
                    if (res.isSuccessful) {
                        result = ResponseEntity.ok(res.body())
                    }
                }
            }
            job.join()
        }
        return result
    }
}