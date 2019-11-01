package spartons.com.prosmssenderapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDatabase


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/27/19}
 */

val roomModule = module {

    single {
        BulkSmsDatabase.getInstance(androidContext())
    }

    single {
        get<BulkSmsDatabase>().bulkSmsDao()
    }
}