package net.polvott.plugins

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.accept
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import net.polvott.dto.User

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        accept(ContentType.Application.Json) {
            post("/login") {
                val user = call.receive<User>()
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
