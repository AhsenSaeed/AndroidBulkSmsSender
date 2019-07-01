package spartons.com.prosmssenderapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import spartons.com.prosmssenderapp.di.qualifiers.ApplicationContextQualifier
import spartons.com.prosmssenderapp.di.scopes.CustomApplicationScope


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Module
class ApplicationContextModule constructor(private val context: Context) {

    @Provides
    @CustomApplicationScope
    @ApplicationContextQualifier
    fun getContext() = context
}