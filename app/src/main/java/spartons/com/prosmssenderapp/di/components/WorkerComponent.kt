package spartons.com.prosmssenderapp.di.components

import dagger.Subcomponent
import spartons.com.prosmssenderapp.di.scopes.WorkerScope
import spartons.com.prosmssenderapp.workers.SendBulkSmsWorker


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/27/19}
 */


@WorkerScope
@Subcomponent
interface WorkerComponent {

    fun inject(sendBulkSmsWorker: SendBulkSmsWorker)
}