package com.simplekjl.servers.storage

import android.content.Context
import android.content.SharedPreferences
import com.simplekjl.servers.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManagerImpl(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Function to delete auth token
     */
    fun deleteAuthToken() {
        prefs.edit().clear().apply()
    }
}