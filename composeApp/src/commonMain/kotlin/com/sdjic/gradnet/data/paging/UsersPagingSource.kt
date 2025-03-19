package com.sdjic.gradnet.data.paging

import com.sdjic.gradnet.data.network.entity.dto.UserDto
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.BasePagingSource
import com.sdjic.gradnet.domain.repo.UserRepository
import kotlin.properties.Delegates

class UsersPagingSource(
    private val userRepository: UserRepository
) : BasePagingSource<UserDto>() {


    private var query by Delegates.notNull<String>()

    fun intiQuery(value: String) {
        query = value
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<UserDto> {
        var result = PaginationItems<UserDto>(
            items = emptyList(),
            page = 0,
            total = 0
        )
        userRepository.getUsers(
            page = page,
            query = "",
            pageSize = limit,
        ).onSuccess { r ->
            r.value?.let { response ->
                result = PaginationItems(
                    items = response.users,
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
