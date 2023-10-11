package de.coasterfreak.isekaibackend

import de.coasterfreak.isekaibackend.api.RestAPI
import de.coasterfreak.isekaibackend.utils.Environment
import de.coasterfreak.isekaibackend.utils.MongoDbHelper
import dev.fruxz.ascend.extension.logging.getItsLogger

class IsekaiBackend {

    /**
     * Represents a variable that holds a RestAPI instance.
     *
     * The RestAPI class is responsible for starting and stopping the REST API server.
     *
     * @property restAPI The RestAPI instance representing the REST API server.
     */
    private var restAPI: RestAPI

    init {
        getItsLogger().info("Starting Isekai Backend Service...")

        // Connect to the database.
        MongoDbHelper.connect()

        restAPI = RestAPI()
        restAPI.startUp(Environment.getEnv("WEBSERVER_PORT")?.toInt() ?: 8080)

        // Add a shutdown hook to stop the server when the JVM is shutting down.
        Runtime.getRuntime().addShutdownHook(Thread {
            restAPI.shutDown()
            // Gracefully shutdown the database connection.
            MongoDbHelper.disconnect()
        })

        getItsLogger().info("Isekai Backend Service started.")
    }


}