package de.coasterfreak.isekaibackend.api.endpoints

import de.coasterfreak.isekaibackend.model.*
import de.coasterfreak.isekaibackend.utils.MongoDbHelper
import io.javalin.http.Context
import io.javalin.http.Handler
import org.litote.kmongo.distinct
import org.litote.kmongo.div

class FiltersEndpoint : Handler {
    override fun handle(ctx: Context) {
        try {
            val collection = MongoDbHelper.getCollection()

            // Abrufen der eindeutigen Werte für jeden Filter
            val genres = collection.distinct<String>("tags.genre").toList()
            val moods = collection.distinct<String>("tags.mood").toList()
            val eras = collection.distinct<String>("tags.era").toList()
            val countries = collection.distinct<String>("originCountry").toList()
            val protagonistGenders = collection.distinct<String>("protagonist.gender").toList()
            val licenseStatuses = collection.distinct<Boolean>("licenseStatus.isLicensed").toList()
            val languages = collection.distinct<String>("languages.language").toList()

            // Erstellen und Zurückgeben eines JSON-Objekts mit allen Filterwerten
            val filters = mapOf(
                "genres" to genres,
                "moods" to moods,
                "eras" to eras,
                "countries" to countries,
                "protagonistGenders" to protagonistGenders,
                "licenseStatuses" to licenseStatuses,
                "languages" to languages
            )
            ctx.json(filters)
        } catch (e: Exception) {
            ctx.json(e)
        }
    }
}