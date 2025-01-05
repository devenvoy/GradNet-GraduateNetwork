package com.sdjic.gradnet.domain.utils

data class PaginationItems<T>(
    val items: List<T>,
    val page: Int,
    val total: Long
)