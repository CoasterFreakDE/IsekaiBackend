package de.coasterfreak.isekaibackend.model

import kotlinx.serialization.Serializable

/**
 * The Character class represents a character in an anime.
 *
 * @property name The name of the character.
 * @property role The role of the character.
 * @property description A description of the character.
 */
@Serializable
data class Character(
    val name: String = "Unknown",
    val role: String = "Unknown",
    val description: String = "Unknown"
)