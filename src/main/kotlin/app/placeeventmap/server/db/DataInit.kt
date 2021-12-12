package app.placeeventmap.server.db

import app.placeeventmap.server.db.repositories.PlaceRepository
import app.placeeventmap.server.db.repositories.ProfileRepository
import app.placeeventmap.server.models.Place
import app.placeeventmap.server.models.Profile
import app.placeeventmap.server.security.services.JwtUserDetailsService
import org.springframework.boot.ApplicationRunner
import kotlin.Throws
import org.springframework.boot.ApplicationArguments
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class DataInit(
    val placeRepository: PlaceRepository,
    val profileRepository: ProfileRepository,
    val userDetailsService: JwtUserDetailsService
) : ApplicationRunner {


    override fun run(args: ApplicationArguments) {
        placeRepository.save(Place("Dense forest"))
        placeRepository.save(Place("My picnic place"))
        placeRepository.save(Place("Gobi Desert", Place.Status.VERIFIED))
        placeRepository.save(Place("Siberian Lake", Place.Status.VERIFIED))
        placeRepository.save(Place("Moscow City", Place.Status.VERIFIED))
        placeRepository.save(Place("Amsterdam", Place.Status.VERIFIED))
        placeRepository.save(Place("Toronto", Place.Status.VERIFIED))

    }
}