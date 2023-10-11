package de.coasterfreak.isekaibackend.model

import kotlinx.serialization.Serializable

/**
 * Represents an anime.
 *
 * @property title The title of the anime.
 * @property description The description of the anime.
 * @property seasons The number of seasons of the anime.
 * @property episodes The number of episodes of the anime.
 * @property streamLink The link to stream the anime.
 * @property protagonist The protagonist of the anime.
 * @property tags The tags associated with the anime.
 * @property originCountry The country of origin of the anime.
 * @property releaseDate The release date of the anime in milliseconds since epoch.
 * @property studio The studio that produced the anime.
 * @property rating The rating of the anime.
 * @property licenseStatus The license status of the anime.
 * @property languages The languages available for the anime.
 * @property characters The characters in the anime.
 */
@Serializable
data class Anime(
    val title: String = "Unknown",
    val description: String = "Unknown",
    val seasons: Int = 0,
    val episodes: Int = 0,
    val streamLink: String = "Unknown",
    val protagonist: Protagonist = Protagonist(),
    val tags: Tags = Tags(),
    val originCountry: String = "Unknown",
    val releaseDate: Long = 0,
    val studio: String = "Unknown",
    val rating: Double = 0.0,
    val licenseStatus: LicenseStatus? = null,
    val languages: List<Language> = emptyList(),
    val characters: List<Character> = emptyList()
)