package spartons.com.prosmssenderapp.util

import okhttp3.logging.HttpLoggingInterceptor
import spartons.com.prosmssenderapp.BuildConfig


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */


val debugLevel = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor.Level.BODY
} else {
    HttpLoggingInterceptor.Level.NONE
}