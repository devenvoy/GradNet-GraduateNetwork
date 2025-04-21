package com.sdjic.gradnet.data.paging

import com.sdjic.gradnet.data.network.entity.dto.LostItemDto
import com.sdjic.gradnet.data.network.repo.GeneralRepository
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.BasePagingSource

class LostItemsPagingSource(
    private val generalRepository: GeneralRepository
) : BasePagingSource<LostItemDto>() {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<LostItemDto> {
        var result = PaginationItems<LostItemDto>(
            items = emptyList(),
            page = 0,
            total = 0
        )
        generalRepository.getLostItems(
            page = page,
           perPage = limit
        ).onSuccess { r ->
            r.value?.let { response ->
                result = PaginationItems(
                    items = response.entries,
                    page = response.page,
                    total = response.perPage.toLong()
                )
            }
        }.onError {
            result = PaginationItems(
                items = emptyList(),
                page = 0,
                total = 0
            )
        }
        return result
    }
}
