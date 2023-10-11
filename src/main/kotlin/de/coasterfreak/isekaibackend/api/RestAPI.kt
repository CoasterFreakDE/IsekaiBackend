package de.coasterfreak.isekaibackend.api

import de.coasterfreak.isekaibackend.model.Anime
import de.coasterfreak.isekaibackend.utils.Environment
import de.coasterfreak.isekaibackend.utils.MongoDbHelper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import io.javalin.http.Header
import io.javalin.http.servlet.JavalinServletContext

/**
 * This class represents a REST API server.
 *
 * The RestAPI class provides methods to start and stop the server.
 * The server is based on Javalin, a simple and lightweight web framework for Java and Kotlin.
 * It allows defining routes and handling HTTP requests and responses.
 *
 * @property apiServer The Javalin instance representing the API server.
 */
class RestAPI {

    private lateinit var apiServer: Javalin

    fun startUp(port: Int) {
        apiServer = Javalin.create { javalinConfig ->
            javalinConfig.plugins.enableCors { corsContainer ->
                corsContainer.add {
                    it.anyHost()
                }
            }
        }
            .before {
                it.header("Access-Control-Allow-Origin", "*")
            }
            .routes {
                get { ctx ->
                    try {
                        ctx.json(MongoDbHelper.getCollection().find().toList())
                    } catch (e: Exception) {
                        ctx.json(e)
                    }
                }
                post { ctx ->
                    val (tokenType, uid) = ctx.header(Header.AUTHORIZATION)?.split(" ")?.toTypedArray() ?: return@post ctx.denyAccess()
                    if (tokenType != "Bearer") return@post ctx.denyAccess()

                    if (uid != Environment.getEnv("POST_KEY")) return@post ctx.denyAccess()

                    try {
                        ctx.json(MongoDbHelper.getCollection().insertOne(ctx.bodyAsClass(Anime::class.java)).wasAcknowledged())
                    } catch (e: Exception) {
                        ctx.json(e)
                    }
                }
            }
            .start(port)
    }

    fun shutDown() {
        apiServer.stop()
    }

    /**
     * This method denies access to the current endpoint.
     *
     * It sets the HTTP status code to 403 (Forbidden) and sends a response message indicating that the user is not allowed to access the endpoint. It also logs the IP address of the user and clears any pending tasks associated with the request.
     *
     * @receiver The context object representing the current HTTP request and response.
     */
    private fun Context.denyAccess() {
        status(403)
        result("You are not allowed to access this endpoint. Your IP has been logged. If you believe this is a mistake, please contact the server owner.")
        (this as JavalinServletContext).tasks.clear()
        val ip = header("X-Forwarded-For")
        println("Someone tried to access the data endpoint with wrong credentials from $ip")
    }
}