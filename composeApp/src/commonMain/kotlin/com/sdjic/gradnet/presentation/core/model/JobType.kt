package com.sdjic.gradnet.presentation.core.model

sealed class JobType(val type: String) {
    data object FullTime : JobType("Full-time")
    data object PartTime : JobType("Part-time")
    data object Remote : JobType("Remote")
    data object Internship : JobType("Internship")
    data object Contract : JobType("Contract")
    data object Hybrid : JobType("Hybrid")
}