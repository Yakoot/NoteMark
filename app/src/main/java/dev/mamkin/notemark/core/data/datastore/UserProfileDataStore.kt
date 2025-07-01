package dev.mamkin.notemark.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create the DataStore instance using a property delegate
val Context.userProfileDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

class UserProfileDataStore(private val context: Context) {

    private object PreferencesKeys {
        val USERNAME = stringPreferencesKey("username")
    }

    val usernameFlow: Flow<String> = context.userProfileDataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USERNAME] ?: ""
        }

    suspend fun updateUsername(username: String) {
        context.userProfileDataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
        }
    }
}