package spartons.com.prosmssenderapp.di.components

import com.squareup.picasso.Picasso
import dagger.Component
import spartons.com.prosmssenderapp.di.modules.HelperModule
import spartons.com.prosmssenderapp.di.modules.PicassoModule
import spartons.com.prosmssenderapp.di.modules.SharedPreferenceModule
import spartons.com.prosmssenderapp.di.modules.UtilModule
import spartons.com.prosmssenderapp.di.scopes.CustomApplicationScope
import spartons.com.prosmssenderapp.helper.NotificationHelper
import spartons.com.prosmssenderapp.helper.SharedPreferenceHelper
import spartons.com.prosmssenderapp.helper.UiHelper
import spartons.com.prosmssenderapp.roomPersistence.BulkSmsDao
import spartons.com.prosmssenderapp.util.ViewModelFactory


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@CustomApplicationScope
@Component(modules = [PicassoModule::class, HelperModule::class, UtilModule::class, SharedPreferenceModule::class])
interface AppComponent {

    fun getPicasso(): Picasso

    fun uiHelper(): UiHelper

    fun viewModelFactory(): ViewModelFactory

    fun notificationHelper(): NotificationHelper

    fun bulkSmsDao(): BulkSmsDao

    fun sharedPreferenceHelper(): SharedPreferenceHelper

    fun activityComponent(): ActivityComponent

    fun fragmentComponent(): FragmentComponent

    fun workerComponent(): WorkerComponent
}