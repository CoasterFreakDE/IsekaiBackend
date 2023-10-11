package de.coasterfreak.isekaibackend.api.endpoints

import de.coasterfreak.isekaibackend.model.*
import de.coasterfreak.isekaibackend.utils.MongoDbHelper
import io.javalin.http.Context
import io.javalin.http.Handler
import org.bson.conversions.Bson
import org.litote.kmongo.*

class DataEndpoint : Handler {

    override fun handle(ctx: Context) {
        try {
            val collection = MongoDbHelper.getCollection()

            // Erstellung einer Liste von Filtern basierend auf den Query-Parametern
            val filters = mutableListOf<Bson>()

            ctx.queryParam("genre")?.let { filters.add(Anime::tags / Tags::genre contains it) }
            ctx.queryParam("mood")?.let { filters.add(Anime::tags / Tags::mood contains it) }
            ctx.queryParam("era")?.let { filters.add(Anime::tags / Tags::era eq it) }
            ctx.queryParam("country")?.let { filters.add(Anime::originCountry eq it) }
            ctx.queryParam("rating")?.let { filters.add(Anime::rating gte it.toDouble()) }
            ctx.queryParam("protagonistGender")?.let { filters.add(Anime::protagonist / Protagonist::gender eq it) }
            ctx.queryParam("protagonistAgeMin")
                ?.let { filters.add(Anime::protagonist / Protagonist::age gte it.toInt()) }
            ctx.queryParam("protagonistAgeMax")
                ?.let { filters.add(Anime::protagonist / Protagonist::age lte it.toInt()) }
            ctx.queryParam("licenseStatus")
                ?.let { filters.add(Anime::licenseStatus / LicenseStatus::isLicensed eq (it == "Licensed")) }
            ctx.queryParam("language")?.let { filters.add(Anime::languages / Language::language eq it) }

            ctx.queryParam("searchString")?.let { filters.add(Anime::title regex Regex(it, RegexOption.IGNORE_CASE)) }

            // Erstellung der Abfrage mit allen Filtern
            val query = if (filters.isNotEmpty()) collection.find(and(filters)) else collection.find()

            // Sortierung hinzufügen, falls ein Sortierparameter angegeben ist
            val sortParam = ctx.queryParam("sort")
            val sortQuery: Bson? = when (sortParam) {
                "title" -> ascending(Anime::title)
                "releaseDate" -> descending(Anime::releaseDate)
                "rating" -> descending(Anime::rating)
                "protagonistAge" -> descending(Anime::protagonist / Protagonist::age)
                else -> null
            }
            if (sortQuery != null) query.sort(sortQuery)

            // Abfrage ausführen und Ergebnisse als JSON zurückgeben
            ctx.json(query.toList())
        } catch (e: Exception) {
            ctx.json(e)
        }
    }
}