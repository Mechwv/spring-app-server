package app.placeeventmap.server.security.services

import app.placeeventmap.server.db.repositories.ProfileRepository
import app.placeeventmap.server.models.Profile
import org.springframework.stereotype.Service

@Service
class ProfileService(private val profileRepository: ProfileRepository) {

    fun save(profile: Profile): Profile {
        return this.profileRepository.save(profile)
    }
}