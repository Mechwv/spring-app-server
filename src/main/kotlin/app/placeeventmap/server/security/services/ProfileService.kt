package app.placeeventmap.server.security.services

import app.placeeventmap.server.db.repositories.PlaceRepository
import app.placeeventmap.server.db.repositories.ProfileRepository
import app.placeeventmap.server.models.Place
import app.placeeventmap.server.models.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProfileService(private val profileRepository: ProfileRepository) {

    fun save(profile: Profile): Profile {
        return this.profileRepository.save(profile)
    }
}

interface PlaceService {
    fun findPlacesByOwnerId(ownerId: UUID): List<Place>
    fun findPlacesByStatus(status: Place.Status): List<Place>
    suspend fun updateWithDownloadedValues(ownerId: UUID, places: List<Place>)
}

@Service
class PlaceServiceImpl(private val placeRepository: PlaceRepository): PlaceService {

    override fun findPlacesByOwnerId(ownerId: UUID): List<Place> {
       return placeRepository.findPlacesByOwnerId(ownerId)
    }

    override fun findPlacesByStatus(status: Place.Status): List<Place> {
        return placeRepository.findPlacesByStatus(status)
    }
    override suspend fun updateWithDownloadedValues(ownerId: UUID, places: List<Place>) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            placeRepository.deleteAllByOwnerId(ownerId = ownerId)
        }
        job.join()
        withContext(Dispatchers.IO) {
            placeRepository.saveAll(places)
        }
    }
}