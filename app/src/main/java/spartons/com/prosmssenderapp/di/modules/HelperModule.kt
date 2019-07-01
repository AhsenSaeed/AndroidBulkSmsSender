package spartons.com.prosmssenderapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import spartons.com.prosmssenderapp.di.qualifiers.ApplicationContextQualifier
import spartons.com.prosmssenderapp.di.scopes.CustomApplicationScope
import spartons.com.prosmssenderapp.helper.NotificationHelper
import spartons.com.prosmssenderapp.helper.UiHelper


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Module(includes = [ApplicationContextModule::class])
class HelperModule {

    @Provides
    @CustomApplicationScope
    fun uiHelper(@ApplicationContextQualifier context: Context) = UiHelper(context)

    @Provides
    @CustomApplicationScope
    fun notificationHelper(@ApplicationContextQualifier context: Context) = NotificationHelper(context)
}