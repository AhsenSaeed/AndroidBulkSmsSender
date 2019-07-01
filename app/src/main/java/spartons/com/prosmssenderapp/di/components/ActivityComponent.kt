package spartons.com.prosmssenderapp.di.components

import dagger.Subcomponent
import spartons.com.prosmssenderapp.activities.main.ui.MainActivity
import spartons.com.prosmssenderapp.activities.sendBulkSms.ui.SendBulkSmsActivity
import spartons.com.prosmssenderapp.di.scopes.ActivityScope


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(mainActivity: SendBulkSmsActivity)
}