package app.placeeventmap.server.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.lang.NonNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

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
    var longtitude: Double = 0.0

    @Column
    var name: String = "Default Place"

    @Column
    var description: String? = null

    @Column
    var event_id: Long? = null

    @Column
    var address: String? = ""

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
    }
}
