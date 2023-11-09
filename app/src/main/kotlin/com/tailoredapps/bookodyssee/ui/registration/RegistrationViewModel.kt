package com.tailoredapps.bookodyssee.ui.registration

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.EffectController
import at.florianschuster.control.createEffectController
import com.tailoredapps.bookodyssee.base.control.EffectControllerViewModel
import com.tailoredapps.bookodyssee.core.DataRepo
import com.tailoredapps.bookodyssee.core.model.User
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class RegistrationViewModel(
    dataRepo: DataRepo
) :
    EffectControllerViewModel<RegistrationViewModel.Action, RegistrationViewModel.State, RegistrationViewModel.Effect>() {
    sealed class Action {
        data object OnRegisterClick : Action()
        data class ChangeUsername(val username: String) : Action()
        data class ChangePassword(val password: String) : Action()
        data class ChangeConfirmPassword(val repeatPassword: String) : Action()
    }

    sealed class Mutation {
        data class SetUsername(val username: String) : Mutation()
        data class SetPassword(val password: String) : Mutation()
        data class SetConfirmPassword(val confirmPassword: String) : Mutation()
    }

    data class State(
        val username: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val isError: Boolean = false
    )

    sealed class Effect {
        data object IsSuccess : Effect()
        data object IsError : Effect()
    }

    override val controller: EffectController<Action, State, Effect> =
        viewModelScope.createEffectController<Action, Mutation, State, Effect>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.ChangeUsername -> flow {
                        emit(Mutation.SetUsername(action.username))
                    }

                    is Action.ChangePassword -> flowOf(Mutation.SetPassword(action.password))
                    is Action.ChangeConfirmPassword -> flowOf(Mutation.SetConfirmPassword(action.repeatPassword))
                    is Action.OnRegisterClick -> flow {
                        dataRepo.insertUser(
                            user = User(
                                username = currentState.username,
                                password = currentState.password
                            )
                        )
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetUsername -> previousState.copy(username = mutation.username)
                    is Mutation.SetPassword -> previousState.copy(password = mutation.password)
                    is Mutation.SetConfirmPassword -> previousState.copy(confirmPassword = mutation.confirmPassword)
                }
            }
        )
}