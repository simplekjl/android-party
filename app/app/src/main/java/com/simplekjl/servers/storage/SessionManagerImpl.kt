package com.simplekjl.servers.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.simplekjl.domain.repository.SessionManager
import com.simplekjl.servers.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManagerImpl(context: Context) : SessionManager {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private var prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context.getString(R.string.app_name),
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        const val USER_TOKEN = "user_token"
    }

    override fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    override fun fetchAuthToken(): String {
        return prefs.getString(USER_TOKEN, "") ?: ""
    }

    override fun deleteAuthToken() {
        prefs.edit().clear().apply()
    }
}