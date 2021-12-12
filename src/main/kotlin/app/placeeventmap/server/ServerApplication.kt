package app.placeeventmap.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
