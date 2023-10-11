package de.coasterfreak.isekaibackend.api

import de.coasterfreak.isekaibackend.api.endpoints.DataEndpoint
import de.coasterfreak.isekaibackend.api.endpoints.FiltersEndpoint
import de.coasterfreak.isekaibackend.api.endpoints.InsertEndpoint
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post

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
                get(DataEndpoint())
                post(InsertEndpoint())

                get("/filters", FiltersEndpoint())
            }
            .start(port)
    }

    fun shutDown() {
        apiServer.stop()
    }
}