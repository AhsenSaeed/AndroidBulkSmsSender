package spartons.com.prosmssenderapp

import org.junit.Test
import spartons.com.prosmssenderapp.activities.sendBulkSms.data.toSmsContact

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val list = listOf("0321 8878961", "+92 321 8878961", "03224293270")

    @Test
    fun addition_isCorrect() {
        val smsContacts = list.map { it.toSmsContact() }
            .toList()
        println(smsContacts)
    }
}
