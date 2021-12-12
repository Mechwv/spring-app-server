package app.placeeventmap.server.controllers

import app.placeeventmap.server.security.services.JwtTokenUtil
import app.placeeventmap.server.db.repositories.ProfileRepository
import app.placeeventmap.server.models.Profile
import app.placeeventmap.server.models.ProfileInfo
import app.placeeventmap.server.retrofit.Common
import app.placeeventmap.server.security.services.JwtUserDetailsService
import app.placeeventmap.server.security.services.ProfileService
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController(
    private val userDetailsService: JwtUserDetailsService,
    private val jwtUtils: JwtTokenUtil,
    private val profileRepository: ProfileRepository
) {
    private val common = Common

    @DelicateCoroutinesApi
    @PostMapping("auth")
    fun authentification(@RequestBody profile: Profile): ResponseEntity<String> {
        var result: ResponseEntity<String> = ResponseEntity.badRequest().body("There is no info")
        if (profile.authToken != null) {
            runBlocking {
                val job = GlobalScope.launch {
                    val res = common.getProfileInfo(profile.authToken!!)
                    if (res.isSuccessful) {
//                        val entity = res.body()
                        val userDetails = userDetailsService.loadUserByUsername(profile.authToken!!)
                        if (userDetails != null) {
                            println("THIS USER ALREADY EXISTS: ${profile.authToken!!}")
                            result = ResponseEntity.ok(jwtUtils.generateToken(userDetails))
                        } else {
                            profileRepository.save(profile)
                            val newUserDetails = userDetailsService.loadUserByUsername(profile.authToken!!)
                            result = ResponseEntity.ok(jwtUtils.generateToken(newUserDetails!!))
                        }

                    }
                }
                job.join()
            }
        }
        return result
    }

//    @DelicateCoroutinesApi
//    @PostMapping("profile/info")
//    fun getInfo(@RequestParam token: String): ResponseEntity<ProfileInfo?> {
//        var result: ResponseEntity<ProfileInfo?> = ResponseEntity.badRequest().body(null)
//        runBlocking {
//            val job = GlobalScope.launch {
//                val res = common.getProfileInfo(token)
//                if (res.isSuccessful) {
//                    result = ResponseEntity.ok(res.body())
//                }
//            }
//            job.join()
//        }
//        return result
//    }

}