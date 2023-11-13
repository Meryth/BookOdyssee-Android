package com.tailoredapps.bookodyssee.ui.login

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.EffectController
import at.florianschuster.control.createEffectController
import com.tailoredapps.bookodyssee.base.control.EffectControllerViewModel
import com.tailoredapps.bookodyssee.core.DataRepo
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

class LoginViewModel(
    dataRepo: DataRepo
) :
    EffectControllerViewModel<LoginViewModel.Action, LoginViewModel.State, LoginViewModel.Effect>() {
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
                        dataRepo.getUser(currentState.username)
                            .mapLatest {
                                Timber.d("aaa user $it")
                                if (currentState.password == it.password) {
                                    Timber.d("aaa success!")
                                    emitEffect(Effect.IsSuccess)
                                } else {
                                    Timber.d("aaa error :P")
                                    emit(Mutation.ShowErrorMessage(true))
                                }
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