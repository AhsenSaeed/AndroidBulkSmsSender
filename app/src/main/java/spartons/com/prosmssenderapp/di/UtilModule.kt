package spartons.com.prosmssenderapp.di

import android.content.Context
import org.koin.dsl.module
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper
import spartons.com.prosmssenderapp.helper.UiHelper


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/27/19}
 */

private const val USER_SHARED_PREFERENCE = "spartons.com.prosmssenderapp_user_shared_preference"

val utilModule = module {

    single { UiHelper() }

    single {
        val sharedPreference =
            get<Context>().getSharedPreferences(USER_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        SharedPreferenceHelper(sharedPreference)
    }
}
