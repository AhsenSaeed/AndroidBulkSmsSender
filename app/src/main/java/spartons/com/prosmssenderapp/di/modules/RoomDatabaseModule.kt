package spartons.com.prosmssenderapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import spartons.com.prosmssenderapp.di.qualifiers.ApplicationContextQualifier
import spartons.com.prosmssenderapp.di.scopes.CustomApplicationScope
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDatabase


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */
@Module(includes = [ApplicationContextModule::class])
class RoomDatabaseModule {

    @Provides
    @CustomApplicationScope
    fun bulkSmsDatabase(@ApplicationContextQualifier context: Context) = BulkSmsDatabase.getInstance(context)

    @Provides
    @CustomApplicationScope
    fun bulkSmsDao(bulkSmsDatabase: BulkSmsDatabase) = bulkSmsDatabase.bulkSmsDao()
}