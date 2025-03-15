package com.sdjic.gradnet.data.paging

data class PaginationItems<T>(
    val items: List<T>,
    val page: Int,
    val total: Long
)