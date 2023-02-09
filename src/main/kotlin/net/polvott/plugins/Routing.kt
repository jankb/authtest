package net.polvott.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        authenticate("jwt-auth-issuerA")
        {
            get("/acceptA")
            {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                call.respondText("Hello $username\n")
            }
        }

        authenticate("jwt-auth-issuerA", "jwt-auth-issuerB")
        {
            get("/acceptAB")
            {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val issuer = principal!!.issuer
                call.respondText("Hello $username. (Using token issued by $issuer)\n")
            }
        }

        authenticate("jwt-auth-issuerB", "jwt-auth-issuerA")
        {
            get("/acceptBA")
            {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val issuer = principal!!.issuer
                call.respondText("Hello $username. (Using token issued by $issuer)\n")
            }
        }
    }
}
