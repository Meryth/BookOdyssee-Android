package com.tailoredapps.bookodyssee.ui.registration

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val registrationModule = module {
    singleOf(::RegistrationViewModel)
}