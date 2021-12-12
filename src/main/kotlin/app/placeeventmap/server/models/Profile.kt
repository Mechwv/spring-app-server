package app.placeeventmap.server.models

import org.springframework.lang.NonNull
import java.util.*
import javax.persistence.*

@Entity
class Profile() {
    @NonNull
    @Id
    @GeneratedValue
    @Column
    private val id: UUID? = null

    @Column(unique = true)
    var authToken: String? = null

    @Column
    var role: Role? = null

    enum class Role(val role: String) {
        ADMIN("ADMIN"), MODERATOR("MODERATOR"), USER("USER")
    }
    constructor(
        authToken: String,
        role: Role
    ) : this() {
        this.authToken = authToken
        this.role = role
    }
}