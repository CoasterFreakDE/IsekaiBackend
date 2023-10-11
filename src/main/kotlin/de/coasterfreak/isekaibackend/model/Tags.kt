package de.coasterfreak.isekaibackend.model

import kotlinx.serialization.Serializable

/**
 * Tags class represents the tags associated with an Anime.
 *
 * @property genre A list of genres associated with the Anime.
 * @property mood A list of moods associated with the Anime.
 * @property era The era in which the Anime is set.
 */
@Serializable
data class Tags(
    val genre: List<String> = emptyList(),
    val mood: List<String> = emptyList(),
    val era: String = "Unknown"
)