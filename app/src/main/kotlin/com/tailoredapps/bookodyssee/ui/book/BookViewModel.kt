package com.tailoredapps.bookodyssee.ui.book

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.tailoredapps.bookodyssee.base.control.ControllerViewModel
import com.tailoredapps.bookodyssee.core.DataRepo
import com.tailoredapps.bookodyssee.core.local.LocalBook
import com.tailoredapps.bookodyssee.core.local.ReadingState
import com.tailoredapps.bookodyssee.core.local.SharedPrefs
import com.tailoredapps.bookodyssee.core.model.BookItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import timber.log.Timber

class BookViewModel(
    private val dataRepo: DataRepo,
    private val sharedPrefs: SharedPrefs,
    bookId: String
) : ControllerViewModel<BookViewModel.Action, BookViewModel.State>() {
    sealed class Action {
        data object LoadBookData : Action()
        data object AddBookToReadingList : Action()
        data object RemoveBookFromReadingList : Action()
        data class ChangeReadingState(val readingState: ReadingState) : Action()
    }

    sealed class Mutation {
        data class SetBookData(val bookItem: BookItem) : Mutation()
        data class SetReadingState(val readingState: ReadingState) : Mutation()
    }

    data class State(
        val bookItem: BookItem? = null,
        val readingState: ReadingState = ReadingState.NOT_ADDED
    )

    override val controller: Controller<Action, State> =
        viewModelScope.createController<Action, Mutation, State>(
            initialState = State(),
            actionsTransformer = { actions ->
                merge(
                    actions,
                    flowOf(Action.LoadBookData)
                )
            },
            mutator = { action ->
                when (action) {
                    is Action.LoadBookData -> flow {
                        runCatching {
                            dataRepo.getBookById(bookId)
                        }.onSuccess { bookItem ->
                            emit(Mutation.SetBookData(bookItem = bookItem))
                        }.onFailure {
                            Timber.e("Error when retrieving book information $it")
                        }

                        runCatching {
                            dataRepo.checkBookAdded(userId = sharedPrefs.userId, bookId = bookId)
                        }.onSuccess { exists ->
                            if (exists) {
                                emit(Mutation.SetReadingState(ReadingState.TO_READ))
                            } else {
                                emit(Mutation.SetReadingState(ReadingState.NOT_ADDED))
                            }
                        }.onFailure {
                            Timber.e("Error when checking book $it")
                        }
                    }

                    is Action.AddBookToReadingList -> flow {
                        runCatching {
                            val book = currentState.bookItem?.volumeInfo
                            if (book != null) {
                                dataRepo.insertBook(
                                    book = LocalBook(
                                        userId = sharedPrefs.userId,
                                        bookId = bookId,
                                        authors = book.authors,
                                        title = book.title,
                                        publisher = book.publisher,
                                        publishedDate = book.publishedDate,
                                        pageCount = book.pageCount,
                                        imageLink = book.imageLinks?.thumbnail.orEmpty(),
                                        readingState = ReadingState.TO_READ
                                    )
                                )
                            }
                        }.onSuccess {
                            emit(Mutation.SetReadingState(ReadingState.TO_READ))
                        }.onFailure {
                            Timber.e("Error: Book could not be added $it")
                        }
                    }

                    is Action.ChangeReadingState -> flow {
                        runCatching {
                            dataRepo.updateBook(
                                userId = sharedPrefs.userId,
                                bookId = bookId,
                                readingState = action.readingState
                            )
                        }.onSuccess {
                            emit(Mutation.SetReadingState(action.readingState))
                        }.onFailure {
                            Timber.e("Error: Reading state of book could not be updated $it")
                        }
                    }

                    is Action.RemoveBookFromReadingList -> flow {
                        runCatching {
                            dataRepo.deleteBook(userId = sharedPrefs.userId, bookId = bookId)
                        }.onSuccess {
                            emit(Mutation.SetReadingState(ReadingState.NOT_ADDED))
                        }.onFailure {
                            Timber.e("Error: Book could not be removed $it")
                        }
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetBookData -> previousState.copy(bookItem = mutation.bookItem)
                    is Mutation.SetReadingState -> previousState.copy(readingState = mutation.readingState)
                }
            }
        )
}