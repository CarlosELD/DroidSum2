package com.example.droidsum2.data

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.example.droidsum2.data.AgregarCookiesInterceptor.Companion.PREF_COOKIES
import okhttp3.Interceptor
import okhttp3.Response

@Suppress("DEPRECATION")
class RecibirCookiesInterceptor(private val context: Context) : Interceptor {
    @SuppressLint("MutatingSharedPrefs")
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies = PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet(PREF_COOKIES, HashSet()) as HashSet<String>?

            originalResponse.headers("Set-Cookie").forEach { header ->
                cookies?.add(header)
            }

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putStringSet(PREF_COOKIES, cookies).apply()
        }
        return originalResponse
    }
}
