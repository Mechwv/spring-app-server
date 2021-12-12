package app.placeeventmap.server.security.services

import org.springframework.security.core.userdetails.UserDetailsService
import app.placeeventmap.server.db.repositories.ProfileRepository
import app.placeeventmap.server.models.Profile
import kotlin.Throws
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(val repository: ProfileRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails? {
        val profile: Profile? = repository.findByAuthTokenLike(userName)
        return if (profile != null) {
            User(
                profile.authToken,
                "",
                listOf(SimpleGrantedAuthority(profile.role.toString()))
            )
        } else {
            null
        }
    }
}