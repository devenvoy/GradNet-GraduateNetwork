
package com.sdjic.gradnet.presentation.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SocialUrls(
    val linkedIn: String? = null,
    val github: String? = null,
    val twitter: String? = null,
    val otherUrls: List<String> = emptyList()
)
