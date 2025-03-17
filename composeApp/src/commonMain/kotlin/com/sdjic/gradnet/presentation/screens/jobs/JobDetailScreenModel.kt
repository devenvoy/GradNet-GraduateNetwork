package com.sdjic.gradnet.presentation.screens.jobs

import cafe.adriel.voyager.core.model.ScreenModel
import com.dokar.sonner.ToastType
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.core.model.emptyJob
import com.sdjic.gradnet.presentation.helper.ToastManager
import com.sdjic.gradnet.presentation.helper.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job as IoJob

class JobDetailScreenModel(
    private val jobsRepository: JobsRepository,
    private val prefs: AppCacheSetting
) : ScreenModel {

    private val _jobDetail = MutableStateFlow<Job>(emptyJob())
    val jobDetail: StateFlow<Job> = _jobDetail.asStateFlow()

    fun updateJobDetail(job: Job) {
        _jobDetail.update { job }
    }

    private var ioJob: IoJob = IoJob()

    fun toggleSaveJob() {
        ioJob.cancel()
        ioJob = CoroutineScope(Dispatchers.IO).launch {

            _jobDetail.update {
                it.copy(isSaved = !it.isSaved)
            }

            jobsRepository.toggleSavedJob(_jobDetail.value.id, prefs.accessToken)
                .onSuccess { r ->
                    ToastManager.showMessage(
                        ToastMessage(
                            message = r.detail,
                            type = ToastType.Success,
                        )
                    )
                }.onError { e ->
                    _jobDetail.update {
                        it.copy(isSaved = !it.isSaved)
                    }
                    ToastManager.showMessage(
                        ToastMessage(
                            message = e.detail,
                            type = ToastType.Error,
                        )
                    )
                }
        }
    }
}