package com.tailoredapps.bookodyssee.ui.login

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.EffectController
import at.florianschuster.control.createEffectController
import com.tailoredapps.bookodyssee.base.control.EffectControllerViewModel
import com.tailoredapps.bookodyssee.core.DataRepo
import com.tailoredapps.bookodyssee.core.local.BookOdysseeSharedPrefs
import com.tailoredapps.bookodyssee.core.local.SharedPrefs
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class LoginViewModel(
    private val dataRepo: DataRepo,
    private val sharedPrefs: SharedPrefs
) : EffectControllerViewModel<LoginViewModel.Action, LoginViewModel.State, LoginViewModel.Effect>() {
    sealed class Action {
        data object OnLoginClick : Action()
        data class ChangeUsername(val username: String) : Action()
        data class ChangePassword(val password: String) : Action()
    }

    sealed class Mutation {
        data class SetUsername(val username: String) : Mutation()
        data class SetPassword(val password: String) : Mutation()
        data class ShowErrorMessage(val isError: Boolean) : Mutation()
    }

    data class State(
        val username: String = "",
        val password: String = "",
        val isError: Boolean = false
    )

    sealed class Effect {
        data object IsSuccess : Effect()
    }

    override val controller: EffectController<Action, State, Effect> =
        viewModelScope.createEffectController<Action, Mutation, State, Effect>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.ChangeUsername -> flow {
                        emit(Mutation.SetUsername(action.username))
                    }

                    is Action.ChangePassword -> flow {
                        emit(Mutation.SetPassword(action.password))
                    }

                    is Action.OnLoginClick -> flow {
                        emit(Mutation.ShowErrorMessage(false))
                        runCatching {
                            dataRepo.getUser(currentState.username)
                        }.onSuccess { user ->
                            if (user.password == currentState.password) {
                                sharedPrefs.userId = user.id
                                emitEffect(Effect.IsSuccess)
                            } else {
                                emit(Mutation.ShowErrorMessage(true))
                            }
                        }.onFailure {
                            Timber.e("Error: could not retrieve user from database!")
                        }
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetUsername -> previousState.copy(username = mutation.username)
                    is Mutation.SetPassword -> previousState.copy(password = mutation.password)
                    is Mutation.ShowErrorMessage -> previousState.copy(isError = mutation.isError)
                }
            }
        )
}