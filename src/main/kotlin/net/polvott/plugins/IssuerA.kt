package net.polvott.plugins

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.http.content.file
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticRootFolder
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.accept
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import net.polvott.dto.User
import java.io.File
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import java.util.concurrent.TimeUnit

fun Application.configureIssuerA() {
    routing {
        static("issuerA/.well-known") {
            staticRootFolder = File("support/issuerA")
            file("jwks.json")
        }
      /*  static(".well-known") {
            staticRootFolder = File("support/issuerA")
            file("openid-configuration")
        }*/

        accept(ContentType.Application.Json) {
            post("/login/issuerA") {
                val user = call.receive<User>()
                println("### issuerA ### Got request for JWT from user ${user.username}, ${user.password} ")

                val privateKeyString =
                    this@configureIssuerA.environment.config.property("jwt.issuerA.privateKey").getString()
                val issuer = this@configureIssuerA.environment.config.property("jwt.issuerA.issuer").getString()
                val audience = this@configureIssuerA.environment.config.property("jwt.issuerA.audience").getString()

                val jwkProvider = JwkProviderBuilder(issuer)
                    .cached(10, 24, TimeUnit.HOURS)
                    .rateLimited(10, 1, TimeUnit.MINUTES)
                    .build()

                val pki = "issuerA"
                val publicKey = jwkProvider.get(pki).publicKey
                val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
                val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("username", user.username)
                    .withClaim("scp", "access_as_user")
                    .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                    .withKeyId(pki)
                    .sign(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey))
                call.respond(hashMapOf("token" to token))
            }
        }
    }
}
