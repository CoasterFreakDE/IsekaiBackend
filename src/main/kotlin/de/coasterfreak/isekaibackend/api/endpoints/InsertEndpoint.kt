package de.coasterfreak.isekaibackend.api.endpoints

import de.coasterfreak.isekaibackend.model.Anime
import de.coasterfreak.isekaibackend.utils.Environment
import de.coasterfreak.isekaibackend.utils.MongoDbHelper
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.Header
import io.javalin.http.servlet.JavalinServletContext

class InsertEndpoint : Handler {
    override fun handle(ctx: Context) {
        val (tokenType, uid) = ctx.header(Header.AUTHORIZATION)?.split(" ")?.toTypedArray() ?: return ctx.denyAccess()
        if (tokenType != "Bearer") return ctx.denyAccess()

        if (uid != Environment.getEnv("POST_KEY")) return ctx.denyAccess()

        try {
            ctx.json(MongoDbHelper.getCollection().insertOne(ctx.bodyAsClass(Anime::class.java)).wasAcknowledged())
        } catch (e: Exception) {
            ctx.json(e)
        }
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