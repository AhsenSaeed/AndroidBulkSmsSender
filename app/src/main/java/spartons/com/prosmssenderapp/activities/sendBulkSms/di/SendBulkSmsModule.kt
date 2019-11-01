package spartons.com.prosmssenderapp.activities.sendBulkSms.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import spartons.com.prosmssenderapp.activities.sendBulkSms.viewModel.SendBulkSmsViewModel
import spartons.com.prosmssenderapp.backend.MyCustomApplication


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 10/27/19}
 */

val sendBulkSmsModule = module {

    viewModel { SendBulkSmsViewModel(androidApplication() as MyCustomApplication, get(), get()) }
}