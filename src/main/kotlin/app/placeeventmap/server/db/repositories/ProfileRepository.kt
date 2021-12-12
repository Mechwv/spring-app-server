package app.placeeventmap.server.db.repositories

import app.placeeventmap.server.models.Profile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProfileRepository: JpaRepository<Profile, UUID> {
    fun findByAuthTokenLike(authToken: String?): Profile?
}