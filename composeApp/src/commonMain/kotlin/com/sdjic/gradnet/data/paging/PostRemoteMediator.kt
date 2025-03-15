package com.sdjic.gradnet.data.paging
/*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sdjic.gradnet.data.local.entity.PostRemoteKeys
import com.sdjic.gradnet.data.local.room.dao.PostDao
import com.sdjic.gradnet.data.local.room.dao.PostRemoteKeysDao
import com.sdjic.gradnet.data.network.entity.dto.PostMapper
import com.sdjic.gradnet.data.network.entity.dto.PostTable
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.PostRepository


@ExperimentalPagingApi
class PostRemoteMediator(
    private val postRepository: PostRepository,
    private val postDao: PostDao,
    private val postRemoteKeysDao: PostRemoteKeysDao,
    private val pref: AppCacheSetting
) : RemoteMediator<Int, PostTable>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostTable>
    ): MediatorResult {
        try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = postRepository.getPosts(
                page = currentPage, perPage = 5,
                accessToken = pref.accessToken,
                selectedFilters = emptyList()
            )
            var mResult: MediatorResult = MediatorResult.Error(Exception("empty"))

            response.onSuccess { r ->
                val resultPosts = r.value?.postDtos ?: emptyList()
                val endOfPaginationReached = resultPosts.isEmpty()

                val prevPage = if (currentPage == 1) null else currentPage - 1
                val nextPage = if (endOfPaginationReached) null else currentPage + 1

                if (loadType == LoadType.REFRESH) {
                    postDao.removeAllPosts()
                    postRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = resultPosts.map { post ->
                    PostRemoteKeys(
                        id = post.postId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                postRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                postDao.insertPosts(images = resultPosts.map { PostMapper.mapDtoToTable(it) })

                mResult = MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }.onError { e ->
                mResult = MediatorResult.Error(Exception(e.detail))
            }
            return mResult
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PostTable>
    ): PostRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                postRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PostTable>
    ): PostRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                postRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PostTable>
    ): PostRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                postRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

}*/
