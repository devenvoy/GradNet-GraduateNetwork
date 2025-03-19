package com.sdjic.gradnet.domain.useCases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdjic.gradnet.data.network.entity.dto.UserDto
import com.sdjic.gradnet.data.paging.UsersPagingSource
import com.sdjic.gradnet.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke(limit: Int, query: String): Flow<PagingData<UserDto>> {
        return Pager(
            config = PagingConfig(pageSize = limit, initialLoadSize = limit),
            pagingSourceFactory = {
                val usersPagingSource = UsersPagingSource(userRepository)
                usersPagingSource.intiQuery(query)
                usersPagingSource
            }
        ).flow
    }
}
