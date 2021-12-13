package app.placeeventmap.server.controllers

import app.placeeventmap.server.db.repositories.PlaceRepository
import app.placeeventmap.server.models.Place
import app.placeeventmap.server.models.Profile.Role
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("places")
class PlacesController(
    val placeRepository: PlaceRepository
) {

    @GetMapping("/all")
    fun getOnlinePlaces(authentication: Authentication): ResponseEntity<List<Place>> {
        val role = authentication.authorities.iterator().next().authority
        var status = Place.Status.VERIFIED
        if (role == Role.MODERATOR.role) {
            status = Place.Status.UNVERIFIED
        }
        return ResponseEntity.ok(placeRepository.findPlacesByStatus(status))
    }
}