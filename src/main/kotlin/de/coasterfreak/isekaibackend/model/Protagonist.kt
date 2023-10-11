package de.coasterfreak.isekaibackend.model

import kotlinx.serialization.Serializable

/**
 * Represents a protagonist of an anime.
 *
 * @property gender The gender of the protagonist.
 * @property age The age of the protagonist.
 * @property opLevel The operational level of the protagonist.
 */
@Serializable
data class Protagonist(
    val gender: String = "Unknown",
    val age: Int = 0,
    val opLevel: String = "Unknown"
)