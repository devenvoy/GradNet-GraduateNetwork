package com.sdjic.gradnet.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.network.entity.dto.LostItemDto
import com.sdjic.gradnet.data.network.repo.GeneralRepository
import com.sdjic.gradnet.data.paging.LostItemsPagingSource
import kotlinx.coroutines.flow.Flow

class GetLostItemsUseCase(
    private val generalRepository: GeneralRepository
) {
    operator fun invoke(limit: Int): Flow<PagingData<LostItemDto>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
               LostItemsPagingSource(generalRepository)
            }
        ).flow
    }
}
