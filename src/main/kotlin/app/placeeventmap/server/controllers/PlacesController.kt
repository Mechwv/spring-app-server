package app.placeeventmap.server.controllers

import app.placeeventmap.server.db.repositories.PlaceRepository
import app.placeeventmap.server.models.Place
import app.placeeventmap.server.models.Profile.Role
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("places")
class PlacesController(
    val placeRepository: PlaceRepository
) {
    @PostMapping("/save")
    fun savePlaces(authentication: Authentication, @RequestBody places: List<Place>): ResponseEntity<Boolean> {
        println("PLACES_TO_SAVE: ${places}")
        return ResponseEntity.ok(true)
    }

    @GetMapping("/all")
    fun getOnlinePlaces(authentication: Authentication): ResponseEntity<List<Place>> {
        val role = authentication.authorities.iterator().next().authority
        var status = Place.Status.VERIFIED
        if (role == Role.MODERATOR.role) {
            status = Place.Status.UNVERIFIED
        }
        val places = placeRepository.findPlacesByStatus(status)
        println("PLACES: $places")
        return ResponseEntity.ok(places)
    }
}