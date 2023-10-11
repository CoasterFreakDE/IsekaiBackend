package de.coasterfreak.isekaibackend.model

import kotlinx.serialization.Serializable

/**
 * LicenseStatus represents the license status of an anime in different regions.
 *
 * @property isLicensed Flag indicating whether the anime is licensed or not.
 * @property regions List of regions where the anime is licensed.
 */
@Serializable
data class LicenseStatus(
    val isLicensed: Boolean = false,
    val regions: List<String> = emptyList()
)