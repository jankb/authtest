package net.polvott

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import kotlinx.serialization.json.Json
import net.polvott.plugins.configureIssuerA
import net.polvott.plugins.configureIssuerB
import net.polvott.plugins.configureRouting
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(ContentNegotiation) {
        json(
            Json {
                encodeDefaults = true
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Authentication) {
        jwt("jwt-auth-issuerA") {
            val issuer = this@module.environment.config.property("jwt.issuerA.issuer").getString()
            val myRealm = this@module.environment.config.property("jwt.issuerA.realm").getString()

            val jwkProvider = JwkProviderBuilder(issuer)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.MINUTES)
                .build()

            realm = myRealm
            verifier(jwkProvider, issuer) {
                acceptLeeway(3)
                withIssuer(issuer)
            }
            validate { credential ->
                if ((credential.payload.getClaim("username").asString() != "")) {
                    println("### jwt-auth-issuerA ### Issuer ${credential.issuer}")
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired\n")
            }
        }

        jwt("jwt-auth-issuerB") {
            val issuerB = this@module.environment.config.property("jwt.issuerB.issuer").getString()
            val myRealm = this@module.environment.config.property("jwt.issuerB.realm").getString()

            val jwkProvider = JwkProviderBuilder(issuerB)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.MINUTES)
                .build()

            realm = myRealm
            verifier(jwkProvider, issuerB) {
                acceptLeeway(3)
                withIssuer(issuerB)
            }
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    println("### jwt-auth-issuerB ### Issuer ${credential.issuer}")
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired\n")
            }
        }
    }
    configureIssuerA()
    configureIssuerB()
    configureRouting()
}
