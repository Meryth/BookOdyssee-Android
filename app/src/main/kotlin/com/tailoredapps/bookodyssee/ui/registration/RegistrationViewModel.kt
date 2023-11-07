package com.tailoredapps.bookodyssee.ui.registration

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.EffectController
import at.florianschuster.control.createEffectController
import com.tailoredapps.bookodyssee.base.control.EffectControllerViewModel
import kotlinx.coroutines.flow.flowOf

class RegistrationViewModel() :
    EffectControllerViewModel<RegistrationViewModel.Action, RegistrationViewModel.State, RegistrationViewModel.Effect>() {
    sealed class Action {
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
        object IsSuccess : Effect()
        object IsError: Effect()
    }

    override val controller: EffectController<Action, State, Effect> =
        viewModelScope.createEffectController<Action, Mutation, State, Effect>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.ChangeUsername -> flowOf(Mutation.SetUsername(action.username))
                    is Action.ChangePassword -> flowOf(Mutation.SetPassword(action.password))
                    is Action.ChangeConfirmPassword -> flowOf(Mutation.SetConfirmPassword(action.repeatPassword))
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