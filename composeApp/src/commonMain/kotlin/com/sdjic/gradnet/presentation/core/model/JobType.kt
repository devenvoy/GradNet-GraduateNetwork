package com.sdjic.gradnet.presentation.core.model
/*
import com.maxkeppeker.sheets.core.utils.JvmSerializable

sealed class JobType(val type: String) : JvmSerializable{
    data object FullTime : JobType("Full-time")
    data object PartTime : JobType("Part-time")
    data object Remote : JobType("Remote")
    data object Internship : JobType("Internship")
    data object Contract : JobType("Contract")
    data object Hybrid : JobType("Hybrid")

    companion object {
        fun fromString(type: String): JobType? {
            return when (type) {
                "Full-time" -> FullTime
                "Part-time" -> PartTime
                "Remote" -> Remote
                "Internship", "internship" -> Internship
                "Contract", "contract" -> Contract
                "Hybrid", "hybrid" -> Hybrid
                else -> null
            }
        }
    }
}

*/