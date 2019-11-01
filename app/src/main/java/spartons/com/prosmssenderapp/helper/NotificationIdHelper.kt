package spartons.com.prosmssenderapp.helper

import java.util.concurrent.atomic.AtomicInteger


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/27/19}
 */

object NotificationIdHelper {

    private val c = AtomicInteger(0)

    fun getId(): Int = c.incrementAndGet()

}