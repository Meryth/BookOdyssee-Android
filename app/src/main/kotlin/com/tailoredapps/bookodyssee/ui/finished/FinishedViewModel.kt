package com.tailoredapps.bookodyssee.ui.finished

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.tailoredapps.bookodyssee.base.control.ControllerViewModel
import com.tailoredapps.bookodyssee.core.DataRepo
import com.tailoredapps.bookodyssee.core.local.LocalBook
import com.tailoredapps.bookodyssee.core.local.ReadingState
import com.tailoredapps.bookodyssee.core.local.SharedPrefs
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class FinishedViewModel(
    dataRepo: DataRepo,
    sharedPrefs: SharedPrefs
) :
    ControllerViewModel<FinishedViewModel.Action, FinishedViewModel.State>() {
    sealed class Action {
        data object GetFinishedBooks : Action()
    }

    sealed class Mutation {
        data class SetFinishedBookList(val bookList: List<LocalBook>) : Mutation()
        data class SetLoadingState(val isLoading: Boolean) : Mutation()
    }

    data class State(
        val finishedBookList: List<LocalBook> = emptyList(),
        val isLoading: Boolean = false
    )

    override val controller: Controller<Action, State> =
        viewModelScope.createController<Action, Mutation, State>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.GetFinishedBooks -> flow {
                        emit(Mutation.SetLoadingState(true))

                        runCatching {
                            dataRepo.getUserBookList(sharedPrefs.userId)
                        }.onSuccess { bookList ->
                            val finishedBooks =
                                bookList.filter { it.readingState == ReadingState.FINISHED }
                            emit(Mutation.SetFinishedBookList(finishedBooks))
                        }.onFailure {
                            Timber.e("Could not retrieve book list of user! $it")
                        }
                        emit(Mutation.SetLoadingState(false))
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetLoadingState -> previousState.copy(isLoading = mutation.isLoading)
                    is Mutation.SetFinishedBookList -> previousState.copy(finishedBookList = mutation.bookList)
                }
            }
        )
}