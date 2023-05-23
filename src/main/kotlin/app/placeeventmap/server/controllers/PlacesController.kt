package app.placeeventmap.server.controllers

import app.placeeventmap.server.db.repositories.ProfileRepository
import app.placeeventmap.server.models.Place
import app.placeeventmap.server.models.Profile.Role
import app.placeeventmap.server.security.services.PlaceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("places")
class PlacesController(
    val placeService: PlaceService,
    val profileRepository: ProfileRepository
) {
    @PostMapping("/save")
    suspend fun savePlaces(authentication: Authentication, @RequestBody places: MutableList<Place>): Any {
        println("\n\n\nPLACES_TO_SAVE: ${places}")
        println(authentication.name)
        val profileId = withContext(Dispatchers.IO) {
            profileRepository.findByAuthTokenLike(authentication.name)
        }?.id
        if (profileId != null) {
            places.forEach {
                it.ownerId = profileId
                println(it.toString())
            }
            placeService.updateWithDownloadedValues(ownerId = profileId, places)
            return ResponseEntity.ok(true)
        }
        return ResponseEntity.badRequest()
    }

    @GetMapping("/download")
    fun getProfilePlaces(authentication: Authentication): Any {
        try {
            val profileId = profileRepository.findByAuthTokenLike(authentication.name)?.id
            val places = placeService.findPlacesByOwnerId(profileId!!)
            println("PLACES: $places")
            return ResponseEntity.ok(places)
        } catch (e: Exception) {
            return ResponseEntity.badRequest()
        }
    }

    @GetMapping("/all")
    fun getOnlinePlaces(authentication: Authentication): ResponseEntity<List<Place>> {
        val role = authentication.authorities.iterator().next().authority
        var status = Place.Status.VERIFIED
        if (role == Role.MODERATOR.role) {
            status = Place.Status.UNVERIFIED
        }
        val places = placeService.findPlacesByStatus(status)
        println("PLACES: $places")
        return ResponseEntity.ok(places)
    }
}