package spartons.com.prosmssenderapp.di.modules

import dagger.Module
import dagger.Provides
import spartons.com.prosmssenderapp.di.scopes.CustomApplicationScope
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDao
import spartons.com.prosmssenderapp.util.ViewModelFactory


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Module(includes = [RoomDatabaseModule::class])
class UtilModule {

    @Provides
    @CustomApplicationScope
    fun viewModelFactory(bulkSmsDao: BulkSmsDao) = ViewModelFactory(bulkSmsDao)
}