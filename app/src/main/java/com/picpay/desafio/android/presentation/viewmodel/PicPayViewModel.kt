package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.utils.Event
import com.picpay.desafio.android.utils.SimpleEvent
import com.picpay.desafio.android.utils.triggerEvent
import com.picpay.desafio.android.utils.triggerPostEvent
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.presentation.mapper.PicPayPresentation
import com.picpay.desafio.android.presentation.model.UserPresentation
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class PicPayViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private var currentList: List<UserPresentation> = emptyList()

    private val _emptyResponseEvent = MutableLiveData<SimpleEvent>()
    private val _fullResultResponseEvent = MutableLiveData<SimpleEvent>()
    private val _errorResponseEvent = MutableLiveData<SimpleEvent>()
    private val _showLoadingEvent = MutableLiveData<SimpleEvent>()
    private val _showScrollLoadingEvent = MutableLiveData<SimpleEvent>()
    private val _showToast = MutableLiveData<SimpleEvent>()
    private val _successResponseEvent = MutableLiveData<Event<List<UserPresentation>>>()

    val emptyResponseEvent: LiveData<SimpleEvent>
        get() = _emptyResponseEvent
    val fullResultResponseEvent: LiveData<SimpleEvent>
        get() = _fullResultResponseEvent
    val errorResponseEvent: LiveData<SimpleEvent>
        get() = _errorResponseEvent
    val successResponseEvent: LiveData<Event<List<UserPresentation>>>
        get() = _successResponseEvent
    val loadingEvent: LiveData<SimpleEvent>
        get() = _showLoadingEvent
    val scrollLoadingEvent: LiveData<SimpleEvent>
        get() = _showScrollLoadingEvent
    val showToast: LiveData<SimpleEvent>
        get() = _showToast

    fun getUsers(isScrolling: Boolean = false) {
        if (currentList.isNullOrEmpty() || isScrolling) {
            requestList(isScrolling)
        } else {
            _successResponseEvent.triggerEvent(currentList)
        }
    }

    private fun requestList(isScrolling: Boolean) {
        viewModelScope.launch(dispatcher) {
            handlerLoading(isScrolling)

            runCatching {
                getUsersUseCase()
            }.onSuccess {
                handlerSuccess(it)
            }.onFailure {
                if (isScrolling) {
                    _showToast.triggerEvent()
                } else {
                    _errorResponseEvent.triggerEvent()
                }
            }
        }
    }

    private fun handlerSuccess(data: PicPayPresentation) {
        when (data) {
            is PicPayPresentation.EmptyResponse -> _emptyResponseEvent.triggerEvent()
            is PicPayPresentation.ErrorResponse -> _errorResponseEvent.triggerEvent()
            is PicPayPresentation.SuccessResponse -> {
                data.items.let {
                    currentList = it
                }
                _successResponseEvent.triggerPostEvent(currentList)
            }
        }
    }

    private fun handlerLoading(isScrolling: Boolean) {
        if (isScrolling) {
            _showScrollLoadingEvent.triggerEvent()
        } else {
            _showLoadingEvent.triggerEvent()
        }
    }
}