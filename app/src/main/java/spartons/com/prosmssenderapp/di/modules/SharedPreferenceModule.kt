package spartons.com.prosmssenderapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import spartons.com.prosmssenderapp.di.qualifiers.ApplicationContextQualifier
import spartons.com.prosmssenderapp.di.scopes.CustomApplicationScope
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/30/19}
 */

@Module(includes = [ApplicationContextModule::class])
class SharedPreferenceModule {

    private companion object {
        private const val USER_SHARED_PREFERENCE = "spartons.com.prosmssenderapp_user_shared_preference"
    }

    @Provides
    @CustomApplicationScope
    fun getSharedPreference(@ApplicationContextQualifier context: Context): SharedPreferences =
        context.getSharedPreferences(USER_SHARED_PREFERENCE, Context.MODE_PRIVATE)

    @Provides
    @CustomApplicationScope
    fun sharedPreferenceHelper(sharedPreferences: SharedPreferences) =
        SharedPreferenceHelper(sharedPreferences)
}