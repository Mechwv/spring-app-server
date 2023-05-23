package app.placeeventmap.server.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.lang.NonNull
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import kotlin.random.Random

@Entity
class Place() {
    @NonNull
    @Id
    @GeneratedValue
    @Column
    var id: Int = 0

    @Column
    var latitude: Double = 0.0

    @Column
    var longitude: Double = 0.0

    @Column
    var name: String = "Default Place"

    @Column
    var description: String? = null

    @Column
    var event_id: Long? = null

    @Column
    var address: String? = ""

    @Column
    var ownerId: UUID? = null

    @JsonIgnore
    @Column
    var status: Status? = Status.UNVERIFIED

    enum class Status(val status: String) {
        VERIFIED("VERIFIED"), UNVERIFIED("UNVERIFIED")
    }

    constructor(
        name: String,
        status: Status = Status.UNVERIFIED
    ) : this() {
        this.name = name
        this.status = status
        this.latitude = Random.nextDouble(-90.0,90.0)
        this.longitude = Random.nextDouble(-180.0,180.0)
        this.description = "Mock data ${Random.nextInt(0, 9999)}"
    }

    override fun toString(): String {
        return "Place(id=$id, latitude=$latitude, longitude=$longitude, name='$name', description=$description, event_id=$event_id, address=$address, ownerId=$ownerId, status=$status)"
    }


}
