package app.placeeventmap.server.db.repositories

import app.placeeventmap.server.models.Place
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface PlaceRepository: JpaRepository<Place, UUID> {
    fun findPlacesByStatus(status: Place.Status): List<Place>
    fun findPlacesByOwnerId(ownerId: UUID): List<Place>
    fun deleteAllByOwnerId(ownerId: UUID)
}