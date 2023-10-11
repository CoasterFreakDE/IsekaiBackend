package de.coasterfreak.isekaibackend.model

import kotlinx.serialization.Serializable

/**
 * Class representing a language used in an anime.
 *
 * @property language The name of the language.
 * @property sub Whether the language is available as subtitles.
 * @property dub Whether the language is available as dubbing.
 */
@Serializable
data class Language(
    val language: String = "Unknown",
    val sub: Boolean = false,
    val dub: Boolean = false
)