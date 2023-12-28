package com.tailoredapps.bookodyssee.ui.home

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.tailoredapps.bookodyssee.base.control.ControllerViewModel
import com.tailoredapps.bookodyssee.core.DataRepo
import com.tailoredapps.bookodyssee.core.local.LocalBook
import com.tailoredapps.bookodyssee.core.local.ReadingState
import com.tailoredapps.bookodyssee.core.local.SharedPrefs
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import timber.log.Timber

class HomeViewModel(
    private val dataRepo: DataRepo,
    private val sharedPrefs: SharedPrefs
) : ControllerViewModel<HomeViewModel.Action, HomeViewModel.State>() {

    sealed class Action {
        data object GetSavedBooks : Action()
    }

    sealed class Mutation {
        data class SetReadingList(
            val toReadList: List<LocalBook>,
            val currentlyReadingList: List<LocalBook>
        ) : Mutation()

        data class SetLoadingState(val isLoading: Boolean) : Mutation()
    }

    data class State(
        val toReadList: List<LocalBook> = emptyList(),
        val currentlyReadingList: List<LocalBook> = emptyList(),
        val isLoading: Boolean = false
    )

    override val controller: Controller<Action, State> =
        viewModelScope.createController<Action, Mutation, State>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.GetSavedBooks -> flow {
                        emit(Mutation.SetLoadingState(true))
                        Timber.d("aaa getSavedBooks called")
                        runCatching {
                            dataRepo.getUserBookList(sharedPrefs.userId)
                        }.onSuccess { bookList ->
                            val toReadList =
                                bookList.filter { it.readingState == ReadingState.TO_READ }
                            val currentlyReadingList =
                                bookList.filter { it.readingState == ReadingState.CURRENTLY_READING }

                            emit(
                                Mutation.SetReadingList(
                                    toReadList = toReadList,
                                    currentlyReadingList = currentlyReadingList
                                )
                            )
                        }.onFailure {
                            Timber.e("Error: Could not get saved books from user! $it")
                        }
                        emit(Mutation.SetLoadingState(false))
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetLoadingState -> previousState.copy(isLoading = mutation.isLoading)
                    is Mutation.SetReadingList -> previousState.copy(
                        toReadList = mutation.toReadList,
                        currentlyReadingList = mutation.currentlyReadingList
                    )
                }
            }
        )
}