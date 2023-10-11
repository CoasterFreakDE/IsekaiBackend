package de.coasterfreak.isekaibackend.utils
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import de.coasterfreak.isekaibackend.model.Anime
import dev.fruxz.ascend.extension.logging.getItsLogger
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import java.net.URLEncoder

object MongoDbHelper {
    private val client: MongoClient
    private val database: MongoDatabase

    init {
        val dbHost = Environment.getEnv("MONGO_DB_HOST") ?: "localhost"
        val dbPort = Environment.getEnv("MONGO_DB_PORT")?.toInt() ?: 27017
        val dbName = Environment.getEnv("MONGO_DB_NAME") ?: "anime"
        val dbUserPlain = Environment.getEnv("MONGO_DB_USER") ?: "isekai"
        val dbPasswordPlain = Environment.getEnv("MONGO_DB_PASSWORD") ?: "isekai"
        val dbUserEncoded = URLEncoder.encode(dbUserPlain, "utf-8")
        val dbPasswordEncoded = URLEncoder.encode(dbPasswordPlain, "utf-8")

        val connectionString = "mongodb://$dbUserEncoded:$dbPasswordEncoded@$dbHost:$dbPort/$dbName"

        client = KMongo.createClient(connectionString)
        database = client.getDatabase(dbName)
    }

    fun connect() {
        getItsLogger().info("Listing databases...")
        client.listDatabaseNames().forEach { getItsLogger().info("Database: $it") }

        getItsLogger().info("Asking for collection...")
        val amount = database.getCollection<Anime>().countDocuments()
        getItsLogger().info("Amount of documents in collection: $amount")
    }

    fun disconnect() {
        client.close()
    }

    fun getCollection() = database.getCollection<Anime>("anime")

    fun insertAnime(anime: Anime) {
        getCollection().insertOne(anime)
    }
}